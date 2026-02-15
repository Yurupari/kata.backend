package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.Cart;
import com.haiilo.kata.backend.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCartStatusIn(List<CartStatus> cartStatuses);
}
