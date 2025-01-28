package myapp.controllers;

import jakarta.validation.Valid;
import myapp.exceptions.InvalidInputException;
import myapp.exceptions.ResourceNotFoundException;
import myapp.model.WasteCategory;
import myapp.modelAssemblers.WasteCategoryModelAssembler;
import myapp.services.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @RestController: This annotation indicates that the class is a REST controller in the Spring framework.
 *                  It combines @Controller and @ResponseBody annotations.
 * @RequestMapping("/api/waste-categories"): This annotation maps HTTP requests to handler methods of the controller.
 *                                           The base URL for this controller is /api/waste-categories.
 */
@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    /**
     * @Autowired: This annotation is used to automatically wire the WasteCategoryService bean into the WasteCategoryController class.
     */
    @Autowired
    private WasteCategoryService wasteCategoryService;
    @Autowired
    private WasteCategoryModelAssembler wasteCategoryModelAssembler;

    /**
     * Retrieves all waste categories.
     *
     * @return a list of all WasteCategory entities.
     */

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<WasteCategory>>> getAllWasteCategories() {
        List<WasteCategory> categories = wasteCategoryService.getAllWasteCategories();

        // Convert the list to a CollectionModel with HATEOAS links
        CollectionModel<EntityModel<WasteCategory>> collectionModel = wasteCategoryModelAssembler.toCollectionModel(categories);

        return ResponseEntity.ok(collectionModel);
    }


    /**
     * Retrieves a specific waste category by its ID.
     *
     * @param id the ID of the WasteCategory entity.
     * @return a ResponseEntity containing the WasteCategory entity if found, or 404 Not Found if not.
     */

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<WasteCategory>> getWasteCategoryById(@PathVariable Long id) {
        WasteCategory category = wasteCategoryService.getWasteCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        // Convert WasteCategory to EntityModel with HATEOAS links
        EntityModel<WasteCategory> entityModel = wasteCategoryModelAssembler.toModel(category);

        return ResponseEntity.ok(entityModel);
    }

    /**
     * Creates a new waste category.
     *
     * @param wasteCategory the WasteCategory entity to be created.
     * @return a ResponseEntity containing the created WasteCategory entity and a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<WasteCategory> createWasteCategory(@Valid @RequestBody WasteCategory wasteCategory) {
        WasteCategory createdWasteCategory = wasteCategoryService.createWasteCategory(wasteCategory);
        if (createdWasteCategory == null){
            throw new InvalidInputException();
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(createdWasteCategory);
    }

    /**
     * Updates an existing waste category.
     *
     * @param id the ID of the WasteCategory entity to be updated.
     * @param wasteCategory the WasteCategory entity with updated values.
     * @return a ResponseEntity containing the updated WasteCategory entity and a 200 OK status, or 404 Not Found if the entity does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WasteCategory> updateWasteCategory(@PathVariable Long id, @Valid @RequestBody WasteCategory wasteCategory) {
        WasteCategory updatedWasteCategory = wasteCategoryService.updateWasteCategory(id, wasteCategory);
        if (updatedWasteCategory != null) {
            return ResponseEntity.ok(updatedWasteCategory);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    /**
     * Deletes a specific waste category by its ID.
     *
     * @param id the ID of the WasteCategory entity to be deleted.
     * @return a ResponseEntity with a 204 No Content status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable Long id) {
        WasteCategory category = wasteCategoryService.getWasteCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        wasteCategoryService.deleteWasteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
