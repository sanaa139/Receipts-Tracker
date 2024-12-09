package dev.sana.receipts_tracker.Repository;

import dev.sana.receipts_tracker.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
        SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name
    """)
    boolean existsByName(@Param("name") String name);

    @Query("""
        SELECT c FROM Category c WHERE c.name = :name
    """)
    Optional<Category> findCategoryByName(@Param("name") String name);
}
