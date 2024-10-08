package com.ru.rudov.coffeeMachine.repositories;

import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.utils.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o WHERE o.recipe.id = :recipeId")
    Long countOrdersByRecipeId(Long recipeId);

    @Query("SELECT o FROM Order o WHERE o.datetimeOrdered = (SELECT MAX(o2.datetimeOrdered) FROM Order o2)")
    Optional<Order> findLastOrder();

    Boolean existsByStatus(OrderStatus orderStatus);

    List<Order> findByStatus(OrderStatus orderStatus);

    Page<Order> findAll(Pageable pageable);

    void deleteByDatetimeOrderedBefore(Timestamp cutoffDate);


}
