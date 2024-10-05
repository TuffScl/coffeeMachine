package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.repositories.IngredientRepository;
import com.ru.rudov.coffeeMachine.repositories.OrderRepository;
import com.ru.rudov.coffeeMachine.repositories.RecipeRepository;
import com.ru.rudov.coffeeMachine.utils.OrderQueue;
import com.ru.rudov.coffeeMachine.utils.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RecipeRepository recipeRepository;

   // private final IngredientRepository ingredientRepository;

    //
    private final OrderQueue orderQueue;
    //
    @Autowired
    public OrderService(OrderRepository orderRepository, RecipeRepository recipeRepository, OrderQueue orderQueue) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
      //  this.ingredientRepository = ingredientRepository;

        //
        this.orderQueue = orderQueue;
        // Инициализация очереди с существующими заказами
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.AWAITING);
        orderQueue.initializeProcessing(pendingOrders);

        // Установить callback для завершения заказа
        orderQueue.setOrderCompletionCallback(this::completeOrder);
        //
    }

    @Transactional
    public void createOrder(Order order){
        log.info("Creating new order: {}", order);
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
    public Order updateOrderId(Long id, Order order){
        log.info("Updating order with id: {}", id);
        return orderRepository.findById(id).map(existingRecipe->{
            order.setId(id);
            return orderRepository.save(order);
        }).orElseThrow(()->{
            log.error("Order with id {} does not exist!", id);
            return new EntityNotFoundException("Order with id "+ id+ " does not exist!");
        });
    }

    @Transactional
    public void deleteOrderById(Long id){
        log.info("Deleting order with id: {}", id);
        orderRepository.deleteById(id);
    }


//    @Transactional
//    public void makeOrder(String recipeName) {
//        Recipe recipe = recipeRepository.findByName(recipeName)
//                .orElseThrow(() -> new EntityNotFoundException("Рецепт с именем " + recipeName + " не существует"));
//
//        boolean isEnough = true;
//
//        for (RecipeStep step : recipe.getSteps()) {
//            if (step.getQuantity()!=null) {
//                Ingredient ingredient = ingredientRepository.findById(step.getIngredient().getId())
//                        .orElseThrow(() -> new EntityNotFoundException(
//                                "Ингредиент с id " + step.getIngredient().getId() + " не найден"));
//
//                if (ingredient.getStock() < step.getQuantity()) {
//                    isEnough = false;
//                    break;
//                }
//            }
//        }
//
//        if (isEnough) {
//            for (RecipeStep step : recipe.getSteps()) {
//            if (step.getQuantity()!=null) {
//                    Ingredient ingredient = ingredientRepository.findById(step.getIngredient().getId())
//                            .orElseThrow(() -> new EntityNotFoundException(
//                                    "Ингредиент с id " + step.getIngredient().getId() + " не найден"));
//                    ingredient.setStock(ingredient.getStock() - step.getQuantity());
//                    ingredientRepository.save(ingredient);
//                }
//            }
//
//            Order order = new Order();
//            order.setRecipe(recipe);
//            order.setDatetimeOrdered(new Timestamp(System.currentTimeMillis()));
//            orderRepository.save(order);
//        } else {
//            throw new InsufficientStockException("Не хватает одного или более ингредиентов");
//        }
//    }


    //
    @Transactional
    public void makeOrder(String recipeName) {
        log.info("Making order for recipe: {}", recipeName);
        Recipe recipe = recipeRepository.findByName(recipeName)
                .orElseThrow(() -> {
                    log.error("Recipe with name {} does not exist!", recipeName);
                    return new EntityNotFoundException("Рецепт с именем " + recipeName + " не существует");
                });
        Order order = new Order();
        order.setRecipe(recipe);
        order.setDatetimeOrdered(new Timestamp(System.currentTimeMillis()));
        order.setStatus(OrderStatus.AWAITING);

        log.info("Adding order to queue: {}", order);
        orderQueue.addOrder(order);
    }

    public void completeOrder(Order order) {
        log.info("Completing order: {}", order);
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
    }
    //



    public Long countOrdersByRecipeId(Long recipeId){

        log.info("Counting orders by recipe id: {}", recipeId);
        return orderRepository.countOrdersByRecipeId(recipeId);
    }

    public Order getLastOrder(){
        log.info("Fetching last order");
        return orderRepository.findLastOrder().orElseThrow(()->{
            log.error("No orders found");
            return new EntityNotFoundException("There are no orders!");
        });
    }

    private boolean isMachineBusy() {
        log.info("Checking if the machine is busy");
        return orderRepository.existsByStatus(OrderStatus.IN_PROGRESS);
    }

}
