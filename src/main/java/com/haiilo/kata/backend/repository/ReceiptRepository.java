package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    @Query("""
       SELECT re FROM Receipt re
       WHERE (:id IS NULL OR re.id = :id)
       AND (:cartId IS NULL OR re.cart.id = :cartId)
       """)
    List<Receipt> findByIdOrCartId(
            @Param("id") Long id,
            @Param("cartId") Long cartId
    );
}
