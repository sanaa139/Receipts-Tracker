package dev.sana.receipts_tracker.Repository;

import dev.sana.receipts_tracker.Transaction;
import dev.sana.receipts_tracker.DTO.TransactionDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<TransactionDTO> findAllByOrderByDateDesc(Pageable pageable);
    @Query("""
        SELECT new dev.sana.receipts_tracker.DTO.TransactionDTO(CAST(null AS java.lang.Integer), CAST(null AS java.time.LocalDate), t.recipient, SUM(t.price), CAST(null AS java.lang.Boolean)) FROM Transaction t GROUP BY t.recipient
    """)
    Page<TransactionDTO> getTransactionsGroupedByRecipient(PageRequest pageRequest);

    @Query("""
        SELECT new dev.sana.receipts_tracker.DTO.TransactionDTO(CAST(null AS java.lang.Integer), t.date, CAST(null AS java.lang.String), SUM(t.price), CAST(null AS java.lang.Boolean)) FROM Transaction t GROUP BY t.date
    """)
    Page<TransactionDTO> getTransactionsGroupedByDate(PageRequest pageRequest);
    @Query("""
        SELECT t FROM Transaction t  WHERE t.price = :price
    """)
    List<Transaction> findByPrice(@Param("price") BigDecimal price);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Transaction t SET t.hasBill = :hasBill WHERE t.id = :id
    """)
    void updateBillStatusInTransaction(@Param("hasBill") boolean hasBill, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Product p WHERE p.transactionId = :id
    """)
    void deleteBillById(@Param("id") Integer id);
}