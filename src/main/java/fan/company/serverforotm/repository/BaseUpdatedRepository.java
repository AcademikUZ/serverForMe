package fan.company.serverforotm.repository;

import fan.company.serverforotm.entity.BaseUpdated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BaseUpdatedRepository extends JpaRepository<BaseUpdated, Long> {


   Optional<BaseUpdated> findByDivisionIdAndUpdated(Long division_id, boolean updated);

   Optional<BaseUpdated> findByDivisionId(Long division_id);
}
