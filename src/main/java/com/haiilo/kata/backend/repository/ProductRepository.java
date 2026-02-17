package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(Status status);
}
