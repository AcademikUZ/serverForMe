package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {


    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getOne(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

}
