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

import java.util.Optional;

@Service
public class BaseUpdateService {

    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    BaseUpdatedRepository baseUpdatedRepository;

    public ApiResult updatedBoolean(Long divisionId) {

        Optional<BaseUpdated> optionalBaseUpdated = baseUpdatedRepository.findByDivisionIdAndUpdated(divisionId, true);
        if(optionalBaseUpdated.isEmpty())
            return new ApiResult("Yangi ma'lumot yo'q!", false);

        return new ApiResult("Yangi ma'lumot mavjud!", true);
    }

    public ApiResult editUpdated(Long divisionId, boolean update) {

        Optional<BaseUpdated> optionalBaseUpdated = baseUpdatedRepository.findByDivisionId(divisionId);
        if(optionalBaseUpdated.isEmpty())
            return new ApiResult("Xatolik. Division id not found!", false);

        BaseUpdated baseUpdated = optionalBaseUpdated.get();
        baseUpdated.setUpdated(update);

        baseUpdatedRepository.save(baseUpdated);

        return new ApiResult("SUCCESS!", true);
    }


}
