package myapp.repository;

import myapp.model.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: This annotation is used to indicate that the interface is a Spring Data Repository.
 * It is a specialization of the @Component annotation, allowing for automatic detection through classpath scanning.
 */
@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
}
