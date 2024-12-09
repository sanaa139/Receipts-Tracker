package dev.sana.receipts_tracker.Repository;

import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.Product;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
        SELECT new dev.sana.receipts_tracker.DTO.ProductDTO(CAST(null AS java.lang.Integer),
        p.name, SUM(p.fullPrice), CAST(null AS java.lang.Integer), c.name) FROM Product p JOIN Category c ON p.category.id = c.id GROUP BY p.name, c.name
    """)
    Page<ProductDTO> getProductsGroupedByName(PageRequest pageRequest);

    @Query("""
  
        SELECT new dev.sana.receipts_tracker.DTO.ProductDTO(CAST(null AS java.lang.Integer),
        CAST(null AS java.lang.String), SUM(p.fullPrice), CAST(null AS java.lang.Integer), c.name) FROM Product p JOIN Category c ON p.category.id = c.id GROUP BY c.name
    """)
    Page<ProductDTO> getProductsGroupedByCategory(PageRequest pageRequest);

    @Query("""
        SELECT new dev.sana.receipts_tracker.DTO.ProductDTO(p.id, p.name, p.price, p.quantity, p.fullPrice, p.transactionId, c.name) FROM Product p
        JOIN Category c ON p.category.id = c.id WHERE p.transactionId = :transactionId
    """)
    List<ProductDTO> findByTransactionId(@Param("transactionId") Integer transactionId);

    @Query("""
        SELECT new dev.sana.receipts_tracker.DTO.ProductDTO(p.id, p.name, p.price, p.quantity, p.fullPrice, p.transactionId, c.name) FROM Product p 
        JOIN Category c ON p.category.id = c.id
    """)
    Page<ProductDTO> getProductsWithCategoryName(PageRequest pageRequest);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Product p WHERE p.transactionId = :transactionId
    """)
    void deleteByTransactionId(@Param("transactionId") Integer transactionId);
}
