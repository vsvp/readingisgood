package com.example.readingisgood.repository;

import com.example.readingisgood.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c FROM Customer c WHERE c.email=:email")
    Customer findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE c.id=:id")
    Customer findById(@Param("id") String id);

}
