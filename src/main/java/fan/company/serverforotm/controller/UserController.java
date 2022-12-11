package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.CurrentUser;
import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.RegisterDto;
import fan.company.serverforotm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    /**
     * Userni ro'yxatdan o'tkaziish uchun kerak
     * @param dto orqali quyidagilar keladi
     *            <p>
     *            private String fullName;
     *            <p>
     *            private String username;
     *            <p>
     *            private String password;
     *            <p>
     *            private String prePassword;
     *            <p>
     *            private Long divisionId;
     *            <p>
     *            private Long roleId;
     * @return ApiResult orqali natija qaytadi
     * @PreAuthorize orqali user permissioni (huquqi) tekshiriladi
     * @Valid orqali json qiymatlar to'g'ri kelganligi tekshiriladi
     *
     */


//    @PreAuthorize(value = "hasAuthority('ADD_USER')")   permission (huquq bo'yicha tekshirish)

    @RoleniTekshirish(role = "ADMIN")
    @PostMapping("/addUser")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        ApiResult apiResult = service.register(dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * Userni parametrlarini o'zgartirish uchun kerak
     *  @param id user id si keladi
     * @param dto orqali quyidagilar keladi
     *            <p>
     *            private String fullName;
     *            <p>
     *            private String username;
     *            <p>
     *            private String password;
     *            <p>
     *            private String prePassword;
     *            <p>
     *            private Long divisionId;
     *            <p>
     *            private Long roleId;
     * @return ApiResult orqali natija qaytadi
     * @PreAuthorize orqali user permissioni (huquqi) tekshiriladi
     * @Valid orqali json qiymatlar to'g'ri kelganligi tekshiriladi
     *
     */

//    @PreAuthorize(value = "hasAuthority('EDIT_USER')")

    @RoleniTekshirish(role = "ADMIN")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody RegisterDto dto) {
        ApiResult apiResult = service.edit(id, dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * barcha userlar ro'yxatini olish uchun ishlatiladi
     * @param page sahifalab berish uchun ishlatiladi (page) faqat integer keladi //misol  GET http://localhost:8080/api/user?page=0
     * @return  ApiResult orqali natija qaytadi true or false
     */

//    @PreAuthorize(value = "hasAuthority('VIEW_USER')")

    @RoleniTekshirish(role = "ADMIN")
    @GetMapping
    public HttpEntity<?> getAll(@RequestParam Integer page) {
        Page<Users> all = service.getAll(page);
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(all);
    }

    /**
     * bitta user ro'yxatini olish uchun ishlatiladi
     * @param id user id si keladi
     * @return user qaytadi
     */

//    @PreAuthorize(value = "hasAuthority('VIEW_USER')")

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        Users one = service.getOne(id);
        return ResponseEntity.status(one != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(one);
    }


    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/role")
    public HttpEntity<?> getRole(@CurrentUser Users user) {
        Role role = user.getRole();
        return ResponseEntity.status(role != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(role);
    }

    /**
     * bitta userni o'chirish uchun ishlatiladi
     * delete qilish uchun unga bog'liq barcha narsani o'chirish kerak
     * @param id user id si keladi
     * @return ApiResult orqali natija qaytadi true or false
     */

//    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @RoleniTekshirish(role = "ADMIN")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResult apiResult = service.delete(id);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }


}
