package myapp.repository;

import myapp.model.RecyclingTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecyclingTipRepository extends JpaRepository<RecyclingTip,Long> {
}
