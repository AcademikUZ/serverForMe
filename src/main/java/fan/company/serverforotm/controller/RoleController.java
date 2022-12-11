package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@Tag(name = "RoleController", description = "Barcha rolelar ro'yxatini olish uchun ishlatiladi")
public class RoleController {

    @Autowired
    RoleService service;

    /**
     * Barcha rolelar ro'yxatini olish uchun ishlatiladi
     * @return  ApiResult orqali natija qaytadi true or false
     */

    @RoleniTekshirish(role = "ADMIN")
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Role> all = service.getAll();
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(all);
    }

    /**
     * Bitta user ro'yxatini olish uchun ishlatiladi
     * @param id user id si keladi
     * @return user qaytadi
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        Role one = service.getOne(id);
        return ResponseEntity.status(one != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(one);
    }



}
