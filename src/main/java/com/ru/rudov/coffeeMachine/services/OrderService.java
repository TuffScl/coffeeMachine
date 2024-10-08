package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Ingredient;
import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.models.RecipeStep;
import com.ru.rudov.coffeeMachine.repositories.OrderRepository;
import com.ru.rudov.coffeeMachine.repositories.RecipeRepository;
import com.ru.rudov.coffeeMachine.utils.OrderQueue;
import com.ru.rudov.coffeeMachine.utils.enums.OrderStatus;
import com.ru.rudov.coffeeMachine.utils.errors.InsufficientIngredientException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RecipeRepository recipeRepository;
    private final OrderQueue orderQueue;

    private final IngredientService ingredientService;

    @Autowired
    public OrderService(OrderRepository orderRepository, RecipeRepository recipeRepository, OrderQueue orderQueue, IngredientService ingredientService) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.orderQueue = orderQueue;
        this.ingredientService = ingredientService;

        List<Order> awaitingOrders = orderRepository.findByStatus(OrderStatus.AWAITING);
        orderQueue.initializeProcessing(awaitingOrders);
        orderQueue.setOrderCompletionCallback(this::completeOrder);
    }

    @Transactional
    public void createOrder(Order order){
        log.info("Creating new order with id: {}", order.getId());
        orderRepository.save(order);
    }

    public Order getOrderById(Long id){
        log.info("Fetching order by id: {}", id);
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders(){
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Transactional
    public void updateOrderId(Long id, Order order) {
        log.info("Updating order with id: {}", id);
        orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order with id " + id + " does not exist!"));
        order.setId(id);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrderById(Long id){
        log.info("Deleting order with id: {}", id);
        orderRepository.deleteById(id);
    }

    @Transactional
    public void makeOrder(String recipeName) {
        log.info("Making order for recipe: {}", recipeName);

        Recipe recipe = recipeRepository.findByName(recipeName)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with name " + recipeName + " does not exist"));

        for (RecipeStep step : recipe.getSteps()) {
            Ingredient ingredient = step.getIngredient();
            if (ingredient != null) {
                Long requiredQuantity = step.getQuantity();
                if (ingredient.getStock() < requiredQuantity) {
                    throw new InsufficientIngredientException("Not enough ingredient " + ingredient.getName() + " in stock for this operation.");
                }
            }
        }

        for (RecipeStep step : recipe.getSteps()) {
            Ingredient ingredient = step.getIngredient();
            if (ingredient!=null){
                Long requiredQuantity = step.getQuantity();
                ingredientService.decreaseIngredientStockById(ingredient.getId(), requiredQuantity);
            }
        }

        Order order = new Order();
        order.setRecipe(recipe);
        order.setDatetimeOrdered(new Timestamp(System.currentTimeMillis()));
        order.setStatus(OrderStatus.AWAITING);

        log.info("Adding order to queue: {}", order.getId());
        orderQueue.addOrder(order);

    }

    public void completeOrder(Order order) {
        log.info("Completing order: {}", order.getId());
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
    }

    public Long countOrdersByRecipeId(Long recipeId){
        log.info("Counting orders by recipe id: {}", recipeId);
        return orderRepository.countOrdersByRecipeId(recipeId);
    }

    public Order getLastOrder(){
        log.info("Fetching last order");
        return orderRepository.findLastOrder().orElseThrow(()->
             new EntityNotFoundException("There are no orders!")
        );
    }

    @Transactional
    public void deleteOrdersOlderThanFiveYears(){
        log.info("Deleting orders with age >= 5 years");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -5);
        Timestamp cutoffDate = new Timestamp(calendar.getTimeInMillis());
        orderRepository. deleteByDatetimeOrderedBefore(cutoffDate);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteOldOrdersScheduled() {
        deleteOrdersOlderThanFiveYears();
    }

}
