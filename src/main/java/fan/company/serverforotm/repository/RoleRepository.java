package fan.company.serverforotm.repository;

import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
