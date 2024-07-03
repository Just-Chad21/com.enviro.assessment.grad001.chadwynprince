package myapp.services;

import myapp.model.WasteCategory;
import myapp.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Service: This annotation indicates that the class is a service component in the Spring framework.
 * It is a specialization of the @Component annotation.
 */
@Service
public class WasteCategoryService {
    /**
     * @Autowired: This annotation is used to automatically wire the WasteCategoryRepository bean into the WasteCategoryService class.
     */
    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    /**
     * Retrieves all waste categories from the database.
     *
     * @return a list of all WasteCategory entities.
     */
    public List<WasteCategory> getAllWasteCategories() {
        return wasteCategoryRepository.findAll();
    }

    /**
     * Retrieves a specific waste category by its ID.
     *
     * @param id the ID of the WasteCategory entity.
     * @return an Optional containing the WasteCategory entity if found, or an empty Optional if not.
     */
    public Optional<WasteCategory> getWasteCategoryById(Long id) {
        return wasteCategoryRepository.findById(id);
    }

    /**
     * Creates a new waste category in the database.
     *
     * @param wasteCategory the WasteCategory entity to be created.
     * @return the created WasteCategory entity.
     */
    public WasteCategory createWasteCategory(WasteCategory wasteCategory) {
        return wasteCategoryRepository.save(wasteCategory);
    }

    /**
     * Updates an existing waste category in the database.
     *
     * @param id the ID of the WasteCategory entity to be updated.
     * @param wasteCategory the WasteCategory entity with updated values.
     * @return the updated WasteCategory entity if found, or null if not.
     */
    public WasteCategory updateWasteCategory(Long id, WasteCategory wasteCategory) {
        Optional<WasteCategory> optionalWasteCategory = wasteCategoryRepository.findById(id);
        if (optionalWasteCategory.isPresent()) {
            WasteCategory existingWasteCategory = optionalWasteCategory.get();
            existingWasteCategory.setName(wasteCategory.getName());
            return wasteCategoryRepository.save(existingWasteCategory);
        } else {
            return null;
        }
    }

    /**
     * Deletes a specific waste category by its ID.
     *
     * @param id the ID of the WasteCategory entity to be deleted.
     */
    public void deleteWasteCategory(Long id) {
        wasteCategoryRepository.deleteById(id);
    }
}
