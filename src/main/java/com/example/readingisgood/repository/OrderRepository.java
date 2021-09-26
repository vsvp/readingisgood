package com.example.readingisgood.repository;

import com.example.readingisgood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o WHERE o.id=:id")
    Order findById(@Param("id") String id);

    @Query("SELECT o FROM Order o WHERE o.customerMail=:customerMail")
    Page<Order> findOrdersByCustomerMail(@Param("customerMail") String customerMail, Pageable paging);
}
