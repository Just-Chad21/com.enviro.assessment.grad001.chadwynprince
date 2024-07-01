package myapp.controllers;

import jakarta.validation.Valid;
import myapp.model.DisposalGuidelines;
import myapp.services.DisposalGuidelinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disposal-guidelines")
public class DisposalGuidelinesController {

    @Autowired
    private DisposalGuidelinesService disposalGuidelinesService;

    @GetMapping
    public List<DisposalGuidelines> getAllDisposalGuidelines() {
        return disposalGuidelinesService.getAllDisposalGuidelines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> getDisposalGuidelinesById(@PathVariable Long id) {
        Optional<DisposalGuidelines> disposalGuidelines = disposalGuidelinesService.getDisposalGuidelinesById(id);
        return disposalGuidelines.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DisposalGuidelines> createDisposalGuidelines(@Valid @RequestBody DisposalGuidelines disposalGuidelines) {
        DisposalGuidelines createdDisposalGuidelines = disposalGuidelinesService.createDisposalGuidelines(disposalGuidelines);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisposalGuidelines);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> updateDisposalGuidelines(@PathVariable Long id, @Valid @RequestBody DisposalGuidelines disposalGuidelines) {
        DisposalGuidelines updatedDisposalGuidelines = disposalGuidelinesService.updateDisposalGuidelines(id, disposalGuidelines);
        if (updatedDisposalGuidelines != null) {
            return ResponseEntity.ok(updatedDisposalGuidelines);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisposalGuidelines(@PathVariable Long id) {
        disposalGuidelinesService.deleteDisposalGuidelines(id);
        return ResponseEntity.noContent().build();
    }
}