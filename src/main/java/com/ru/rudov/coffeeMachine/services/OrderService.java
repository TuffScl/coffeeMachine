package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.repositories.OrderRepository;
import com.ru.rudov.coffeeMachine.repositories.RecipeRepository;
import com.ru.rudov.coffeeMachine.utils.OrderQueue;
import com.ru.rudov.coffeeMachine.utils.enums.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, RecipeRepository recipeRepository, OrderQueue orderQueue) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.orderQueue = orderQueue;

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

    public Page<Order> getAllOrders(int page, int size){
        log.info("Fetching orders with pagination - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
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

    @Transactional
    public void makeOrder(String recipeName) {
        log.info("Making order for recipe: {}", recipeName);
        Recipe recipe = recipeRepository.findByName(recipeName)
                .orElseThrow(() -> {
                    log.info("Recipe with name {} does not exist!", recipeName);
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

    @Transactional
    public void deleteOrdersOlderThanFiveYears(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -5);
        Timestamp cutoffDate = new Timestamp(calendar.getTimeInMillis());
        orderRepository.deleteOrdersOlderThan(cutoffDate);
    }


    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    @Transactional
    public void deleteOldOrdersScheduled() {
        deleteOrdersOlderThanFiveYears();
    }

}
