package myapp.repository;

import myapp.model.DisposalGuidelines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisposalGuidelinesRepository extends JpaRepository<DisposalGuidelines, Long> {
}
