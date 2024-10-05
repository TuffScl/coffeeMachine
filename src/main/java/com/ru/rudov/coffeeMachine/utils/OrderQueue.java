package com.ru.rudov.coffeeMachine.utils;

import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Component
public class OrderQueue {
    private final ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<>();
    private boolean isProcessing = false;

    private Consumer<Order> orderCompletionCallback; // Callback для завершения заказа

    public void setOrderCompletionCallback(Consumer<Order> callback) {
        this.orderCompletionCallback = callback;
    }

    public synchronized void addOrder(Order order) {
        queue.add(order);
        processNextOrder();
    }

    public void initializeProcessing(List<Order> pendingOrders) {
        queue.addAll(pendingOrders);
        processNextOrder();
    }

    private void processNextOrder() {
        if (isProcessing) return;

        Order nextOrder = queue.poll();
        if (nextOrder != null) {
            isProcessing = true;
            new Thread(() -> {
                try {
                    Thread.sleep(calculatePreparationTime(nextOrder.getRecipe()));
                    // Здесь мы не обновляем статус заказа, а просто вызываем callback
                    if (orderCompletionCallback != null) {
                        orderCompletionCallback.accept(nextOrder);
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    isProcessing = false;
                    processNextOrder();
                }
            }).start();
        }
    }

    private long calculatePreparationTime(Recipe recipe) {
        // Определите время приготовления
        return recipe.getSteps().size() * 1000L; // Пример: 1 секунда на шаг
    }

    // Callback для обработки завершения заказа
    public void onOrderCompleted(Order order) {
        // Метод будет вызван после завершения заказа
        // Этот метод можно оставить пустым или реализовать как-то иначе.
    }
}
