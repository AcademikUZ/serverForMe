package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.service.BaseUpdateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baseUpdate")
@Tag(name = "BaseUpdateController", description = "Boshqarmaga fayl kelganmi yoki yo'qmi bilish uchun kerak")
public class BaseUpdateController {

    @Autowired
    BaseUpdateService service;

    /**
     * Boshqarmaga fayl kelganmi yoki yo'qmi bilish uchun kerak
     * @param divisionId boshqarma idsi keladi
     * @return natija qaytadi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/updatedBoolean")
    public HttpEntity<?> updatedBoolean(@RequestParam Long divisionId) {
        ApiResult apiResult = service.updatedBoolean(divisionId);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * BaseUpdate tableda ma'lumotni o'zgartirish uchun kerak
     * @param divisionId boshqarma idsi keladi
     * @param update true or false keladi
     * @return natija qaytadi
     */

//
//    @RoleniTekshirish(role = "USER")
//    @PutMapping("/updatedBoolean")
//    public HttpEntity<?> updatedBoolean(@RequestParam Long divisionId, @RequestParam boolean update) {
//        ApiResult apiResult = service.editUpdated(divisionId, update);
//        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
//    }

}
