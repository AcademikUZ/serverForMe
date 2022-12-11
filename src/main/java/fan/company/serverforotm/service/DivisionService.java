package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.BaseUpdated;
import fan.company.serverforotm.entity.Division;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.DivisionDto;
import fan.company.serverforotm.repository.BaseUpdatedRepository;
import fan.company.serverforotm.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    @Autowired
    DivisionRepository repository;
    @Autowired
    BaseUpdatedRepository baseUpdatedRepository;

    public ApiResult add(DivisionDto dto) {

        Optional<Division> optionalDivision = repository.findByName(dto.getName());
        if (optionalDivision.isPresent())
            return new ApiResult("Bunday Division mavjud!", false);


        Division saved = repository.save(new Division(dto.getName()));

        BaseUpdated baseUpdated = new BaseUpdated(saved);
        baseUpdatedRepository.save(baseUpdated);

        return new ApiResult("Muvoffaqiyatli saqlandi!", true, repository.findAll());
    }

    public ApiResult edit(Long id, DivisionDto dto) {

        Optional<Division> optionalDivision = repository.findById(id);
        if (optionalDivision.isEmpty())
            return new ApiResult("Bunday Division mavjud emas!", false);

        Division division = new Division(dto.getName(), dto.isActive());
        division.setId(optionalDivision.get().getId());

        repository.save(division);

        return new ApiResult("Muvoffaqiyatli tahrirlandi!", true, repository.findAll());
    }

    // Sahifalab olish
    public Page<Division> getAllWithPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public List<Division> getAll() {
        return repository.findAll();
    }


    public Division getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (!existsById)
                return new ApiResult("Bunday Division mavjud emas", false);

            repository.deleteById(id);

            return new ApiResult("O'chirildi", true);

        } catch (Exception e) {
            return new ApiResult("Xatolik", false, repository.findAll());
        }
    }

}
