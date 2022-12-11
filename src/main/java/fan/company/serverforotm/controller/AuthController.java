package fan.company.serverforotm.controller;

import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.LoginDto;
import fan.company.serverforotm.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "Autentification uchun")
public class AuthController {

    @Autowired
    AuthService service;

    /**
     * Sistemaga kirish uchun
     * @param dto username va password keladi
     * @return ApiResult natijani beradi true or false
     */

    @PostMapping("/login")
    public HttpEntity<?> login (@Valid @RequestBody LoginDto dto){
        ApiResult apiResult = service.login(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

}
