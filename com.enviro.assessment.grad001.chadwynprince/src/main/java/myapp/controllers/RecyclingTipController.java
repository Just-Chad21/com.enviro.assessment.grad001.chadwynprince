package myapp.controllers;

import myapp.model.RecyclingTip;
import myapp.services.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {

    @Autowired
    private RecyclingTipService service;

    @PostMapping
    public ResponseEntity<RecyclingTip> createRecyclingTip(@RequestBody RecyclingTip recyclingTip) {
        RecyclingTip savedRecyclingTip = service.createRecyclingTip(recyclingTip);
        return ResponseEntity.ok(savedRecyclingTip);
    }

    @GetMapping
    public ResponseEntity<List<RecyclingTip>> getAllRecyclingTips() {
        List<RecyclingTip> recyclingTips = service.getAllRecyclingTips();
        return ResponseEntity.ok(recyclingTips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTip> getRecyclingTipById(@PathVariable Long id) {
        Optional<RecyclingTip> recyclingTip = service.getRecyclingTipById(id);
        return recyclingTip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTip> updateRecyclingTip(@PathVariable Long id, @RequestBody RecyclingTip recyclingTipDetails) {
        RecyclingTip updatedRecyclingTip = service.updateRecyclingTip(id, recyclingTipDetails);
        if (updatedRecyclingTip != null) {
            return ResponseEntity.ok(updatedRecyclingTip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecyclingTip(@PathVariable Long id) {
        service.deleteRecyclingTip(id);
        return ResponseEntity.noContent().build();
    }
}
