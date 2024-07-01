package myapp.controllers;

import jakarta.validation.Valid;
import myapp.model.WasteCategory;
import myapp.services.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    @Autowired
    private WasteCategoryService wasteCategoryService;

    @GetMapping
    public List<WasteCategory> getAllWasteCategories() {
        return wasteCategoryService.getAllWasteCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategory> getWasteCategoryById(@PathVariable Long id) {
        Optional<WasteCategory> wasteCategory = wasteCategoryService.getWasteCategoryById(id);
        return wasteCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WasteCategory> createWasteCategory(@Valid @RequestBody WasteCategory wasteCategory) {
        WasteCategory createdWasteCategory = wasteCategoryService.createWasteCategory(wasteCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWasteCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCategory> updateWasteCategory(@PathVariable Long id, @Valid @RequestBody WasteCategory wasteCategory) {
        WasteCategory updatedWasteCategory = wasteCategoryService.updateWasteCategory(id, wasteCategory);
        if (updatedWasteCategory != null) {
            return ResponseEntity.ok(updatedWasteCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable Long id) {
        wasteCategoryService.deleteWasteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
