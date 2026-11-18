package calamadevs.repository.Maintenance;

import calamadevs.domain.Maintenance.MaintenanceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface MaintenanceItemRepository extends JpaRepository<MaintenanceItem, Long> {
    @Query("""
            SELECT DISTINCT i
            FROM MaintenanceItem i
            JOIN FETCH i.category c
            LEFT JOIN FETCH i.allowedTypes t
            WHERE i.category.id = :categoryId
              AND i.active = true
            ORDER BY i.name ASC
            """)
    List<MaintenanceItem> findActiveByCategoryWithTypes(@Param("categoryId") Long categoryId);
    @Query("""
            SELECT i
            FROM MaintenanceItem i
            LEFT JOIN FETCH i.allowedTypes
            WHERE i.id = :id
            """)
    Optional<MaintenanceItem> findByIdWithAllowedTypes(@Param("id") Long id);
    @Query("""
            SELECT DISTINCT i
            FROM MaintenanceItem i
            JOIN FETCH i.category c
            LEFT JOIN FETCH i.allowedTypes t
            LEFT JOIN MaintenanceItemAlias a ON a.item = i
            WHERE i.active = true
              AND (
                LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.alias) LIKE LOWER(CONCAT('%', :query, '%'))
                OR a.normalizedAlias LIKE LOWER(CONCAT('%', :normalizedQuery, '%'))
              )
            ORDER BY i.name ASC
            """)
    List<MaintenanceItem> searchByNameOrAlias(
            @Param("query") String query,
            @Param("normalizedQuery") String normalizedQuery);
}