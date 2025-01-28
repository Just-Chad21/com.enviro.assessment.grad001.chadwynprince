package myapp.controllers;

import jakarta.validation.Valid;
import myapp.exceptions.InvalidInputException;
import myapp.exceptions.ResourceNotFoundException;
import myapp.model.RecyclingTip;
import myapp.modelAssemblers.RecyclingTipModelAssembler;
import myapp.services.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {

    @Autowired
    private RecyclingTipService service;
    @Autowired
    private RecyclingTipModelAssembler recyclingTipModelAssembler;

    @PostMapping
    public ResponseEntity<RecyclingTip> createRecyclingTip(@Valid @RequestBody RecyclingTip recyclingTip) {
        RecyclingTip savedRecyclingTip = service.createRecyclingTip(recyclingTip);
        if (savedRecyclingTip == null){
            throw new InvalidInputException();
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedRecyclingTip);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RecyclingTip>>> getAllRecyclingTips() {
        List<RecyclingTip> tips = service.getAllRecyclingTips(); // Fetch all tips from the service

        // Convert the list to a CollectionModel
        CollectionModel<EntityModel<RecyclingTip>> collectionModel = recyclingTipModelAssembler.toCollectionModel(tips);

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingTip>> getRecyclingTipById(@PathVariable Long id) {
        RecyclingTip tip = service.getRecyclingTipById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        // Add hypermedia links to the RecyclingTip object

        EntityModel<RecyclingTip> entityModel = recyclingTipModelAssembler.toModel(tip);

        // Convert EntityModel to RecyclingTip for return
        return ResponseEntity.ok(entityModel);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTip> updateRecyclingTip(@PathVariable Long id, @Valid @RequestBody RecyclingTip recyclingTipDetails) {
        RecyclingTip updatedRecyclingTip = service.updateRecyclingTip(id, recyclingTipDetails);
        if (updatedRecyclingTip != null) {
            return ResponseEntity.ok(updatedRecyclingTip);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecyclingTip(@PathVariable Long id) {
        RecyclingTip tip = service.getRecyclingTipById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        service.deleteRecyclingTip(id);
        return ResponseEntity.noContent().build();
    }
}
