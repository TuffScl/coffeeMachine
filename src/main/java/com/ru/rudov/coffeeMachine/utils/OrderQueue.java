package com.ru.rudov.coffeeMachine.utils;

import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Component
public class OrderQueue {
    private final ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<>();
    private boolean isProcessing = false;

    private Consumer<Order> orderCompletionCallback;

    private final Thread processingThread;

    public OrderQueue() {
        processingThread = new Thread(this::processOrders);
        processingThread.start();
    }

    public void setOrderCompletionCallback(Consumer<Order> callback) {
        this.orderCompletionCallback = callback;
    }

    public synchronized void addOrder(Order order) {
        queue.add(order);
    }

    public void initializeProcessing(List<Order> pendingOrders) {
        queue.addAll(pendingOrders);
    }

    private void processOrders() {
        while (true) {
            if (!queue.isEmpty() && !isProcessing) {
                Order nextOrder = queue.poll();
                if (nextOrder != null) {
                    isProcessing = true;
                    try {
                        Thread.sleep(calculatePreparationTime(nextOrder.getRecipe()));
                        if (orderCompletionCallback != null) {
                            orderCompletionCallback.accept(nextOrder);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        isProcessing = false;
                    }
                }
            }
        }
    }

    private long calculatePreparationTime(Recipe recipe) {
        return recipe.getSteps().size() * 1000L;
    }
}
