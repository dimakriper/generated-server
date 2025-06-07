
package org.openapitools.repository;

import org.openapitools.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    // Spring Data JPA provides basic CRUD operations
}