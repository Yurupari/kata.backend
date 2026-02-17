package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByStatus(Status status, Pageable pageable);

    @Query("""
            SELECT p FROM Product p
            WHERE LOWER(p.name) LIKE LOWER(concat('%', :searchQuery, '%'))
            """)
    Page<Product> findByNameContainingIgnoreCase(
            @Param("searchQuery") String searchQuery,
            Pageable pageable
    );
}
