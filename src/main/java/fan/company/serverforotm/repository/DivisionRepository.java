package fan.company.serverforotm.repository;

import fan.company.serverforotm.entity.Division;
import fan.company.serverforotm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division, Long> {

    Optional<Division> findByName(String name);

}
