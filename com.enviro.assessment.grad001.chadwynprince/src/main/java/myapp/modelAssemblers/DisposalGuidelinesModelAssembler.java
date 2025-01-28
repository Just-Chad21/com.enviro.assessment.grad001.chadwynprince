package myapp.modelAssemblers;

import myapp.controllers.DisposalGuidelinesController;
import myapp.controllers.RecyclingTipController;
import myapp.model.DisposalGuidelines;
import myapp.model.RecyclingTip;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class DisposalGuidelinesModelAssembler implements RepresentationModelAssembler<DisposalGuidelines, EntityModel<DisposalGuidelines>> {


    @Override
    public EntityModel<DisposalGuidelines> toModel(DisposalGuidelines disposalGuidelines) {
        EntityModel<DisposalGuidelines> entityModel = EntityModel.of(
                disposalGuidelines,
                linkTo(methodOn(DisposalGuidelinesController.class).getDisposalGuidelinesById(disposalGuidelines.getId())).withSelfRel(),
                linkTo(methodOn(DisposalGuidelinesController.class).getAllDisposalGuidelines()).withRel("disposal-guidelines")
        );
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<DisposalGuidelines>> toCollectionModel(Iterable<? extends DisposalGuidelines> entities) {
        CollectionModel<EntityModel<DisposalGuidelines>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        // Add a link to the "self" endpoint for the collection
        collectionModel.add(linkTo(methodOn(DisposalGuidelinesController.class).getAllDisposalGuidelines()).withSelfRel());

        // Add other links if necessary (e.g., pagination or related endpoints)
        return collectionModel;
    }
}
