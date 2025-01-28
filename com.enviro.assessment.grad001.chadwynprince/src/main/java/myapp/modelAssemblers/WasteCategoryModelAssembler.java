package myapp.modelAssemblers;


import myapp.controllers.WasteCategoryController;
import myapp.model.WasteCategory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WasteCategoryModelAssembler implements RepresentationModelAssembler<WasteCategory, EntityModel<WasteCategory>> {
    @Override
    public EntityModel<WasteCategory> toModel(WasteCategory wasteCategory) {
        // Create an EntityModel for the WasteCategory
        EntityModel<WasteCategory> entityModel = EntityModel.of(
                wasteCategory,
                linkTo(methodOn(WasteCategoryController.class).getWasteCategoryById(wasteCategory.getId())).withSelfRel(),
                linkTo(methodOn(WasteCategoryController.class).getAllWasteCategories()).withRel("waste-categories")
        );
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<WasteCategory>> toCollectionModel(Iterable<? extends WasteCategory> entities) {
        // Create the collection model
        CollectionModel<EntityModel<WasteCategory>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        // Add a link to the "self" endpoint for the collection
        collectionModel.add(linkTo(methodOn(WasteCategoryController.class).getAllWasteCategories()).withSelfRel());

        // Add other links if necessary (e.g., pagination or related endpoints)
        return collectionModel;
    }
}
