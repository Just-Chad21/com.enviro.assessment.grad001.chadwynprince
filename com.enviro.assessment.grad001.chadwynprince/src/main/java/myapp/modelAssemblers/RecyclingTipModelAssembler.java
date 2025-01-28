package myapp.modelAssemblers;

import myapp.controllers.RecyclingTipController;
import myapp.model.RecyclingTip;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class RecyclingTipModelAssembler implements RepresentationModelAssembler<RecyclingTip, EntityModel<RecyclingTip>> {

    @Override
    public EntityModel<RecyclingTip> toModel(RecyclingTip recyclingTip) {
        EntityModel<RecyclingTip> entityModel = EntityModel.of(
                recyclingTip,
                linkTo(methodOn(RecyclingTipController.class).getRecyclingTipById(recyclingTip.getId())).withSelfRel(),
                linkTo(methodOn(RecyclingTipController.class).getAllRecyclingTips()).withRel("recycling-tips")
        );
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<RecyclingTip>> toCollectionModel(Iterable<? extends RecyclingTip> entities) {
        CollectionModel<EntityModel<RecyclingTip>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        // Add a link to the "self" endpoint for the collection
        collectionModel.add(linkTo(methodOn(RecyclingTipController.class).getAllRecyclingTips()).withSelfRel());

        // Add other links if necessary (e.g., pagination or related endpoints)
        return collectionModel;
    }
}
