package myapp.controllers;

import jakarta.validation.Valid;
import myapp.exceptions.InvalidInputException;
import myapp.exceptions.ResourceNotFoundException;
import myapp.model.DisposalGuidelines;
import myapp.modelAssemblers.DisposalGuidelinesModelAssembler;
import myapp.services.DisposalGuidelinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disposal-guidelines")
public class DisposalGuidelinesController {

    @Autowired
    private DisposalGuidelinesService disposalGuidelinesService;

    @Autowired
    private DisposalGuidelinesModelAssembler disposalGuidelinesModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DisposalGuidelines>>> getAllDisposalGuidelines() {
        List<DisposalGuidelines> guidelines = disposalGuidelinesService.getAllDisposalGuidelines();
        CollectionModel<EntityModel<DisposalGuidelines>> collectionModel = disposalGuidelinesModelAssembler.toCollectionModel(guidelines);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DisposalGuidelines>> getDisposalGuidelinesById(@PathVariable Long id) {
        DisposalGuidelines guideline = disposalGuidelinesService.getDisposalGuidelinesById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        EntityModel<DisposalGuidelines> entityModel = disposalGuidelinesModelAssembler.toModel(guideline);
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    public ResponseEntity<DisposalGuidelines> createDisposalGuidelines(@Valid @RequestBody DisposalGuidelines disposalGuidelines) {
        DisposalGuidelines savedDisposalGuideline = disposalGuidelinesService.createDisposalGuidelines(disposalGuidelines);
        if (savedDisposalGuideline== null){
            throw new InvalidInputException();
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedDisposalGuideline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> updateDisposalGuidelines(@PathVariable Long id, @Valid @RequestBody DisposalGuidelines disposalGuidelines) {
        DisposalGuidelines updatedDisposalGuidelines = disposalGuidelinesService.updateDisposalGuidelines(id, disposalGuidelines);
        if (updatedDisposalGuidelines != null) {
            return ResponseEntity.ok(updatedDisposalGuidelines);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisposalGuidelines(@PathVariable Long id) {
        DisposalGuidelines guideline = disposalGuidelinesService.getDisposalGuidelinesById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        disposalGuidelinesService.deleteDisposalGuidelines(id);
        return ResponseEntity.noContent().build();
    }
}