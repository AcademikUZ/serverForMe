package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.CurrentUser;
import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.entity.Attachment;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.AttachmentDto;
import fan.company.serverforotm.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachment")
//Swagger uchun
@Tag(name = "Attachment", description = "Fayllarni yuklash uchun controller")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    /**
     * http://localhost:8080/swagger-ui/index.html#/
     * swaggerni olish uchun manzil
     */
    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * @CurrentUser Sistemaga kirgan userni olish uchun kerak
     * Faylni sistemaga yuklash uchun ishlatiladi (bir nechta fayl yuborsa ham bo'ladi)
     * @param users bu qaysi boshqarmadan fayl jo'natilayotganligini beradi
     * @param toDivision fayl qaysi boshqarmaga ketayotganligini beradi
     * @param file bu orqali form-data orqali fayl keladi
     * @return ApiResult orqali fayl yuklanganligini beradi
     * @throws IOException hatolikni bildiradi
     */

    @RoleniTekshirish(role = "USER")
    @PostMapping("/uploadFileToFileSystem")
    //Swagger uchun
    @Operation(description = " Faylni sistemaga yuklash uchun ishlatiladi (bir nechta fayl yuborsa ham bo'ladi)")
    public HttpEntity<?> uploadFileToFileSystem(@CurrentUser Users users, @RequestParam Long toDivision, MultipartHttpServletRequest file) throws IOException {

        AttachmentDto dto = new AttachmentDto(users.getDivision().getId(), toDivision);
        ApiResult apiResult = attachmentService.uploadFileToFileSystem(users, dto, file);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * @CurrentUser Sistemaga kirgan userni olish uchun kerak
     * Faylni skachat qilish uchun ishlatiladi
     * @param users fayl userni boshqarmasiga tegishlimi yoki yoqmi tekshirish uchun kerak
     * @param id  fayl id si keladi
     * @param response faylni shu orqali response orqali qaytaradi
     * @return ApiResult orqali fayl yuklanganligini beradi
     * @throws IOException hatolikni bildiradi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/downloadFileFromFileSystem/{id}")
    //Swagger uchun
    @Operation(description = "Faylni skachat qilish uchun ishlatiladi")
    public HttpEntity<?> downloadFileFromFileSystem(@CurrentUser Users users, @PathVariable Long id, HttpServletResponse response) {
        ApiResult apiResult = attachmentService.downloadFromFileSystem(users, id, response);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * @CurrentUser Sistemaga kirgan userni olish uchun kerak
     * Boshqarmaga kelgan fayllar ro'yxatini olish uchun ishlatiladi
     * @param users fayl userni boshqarmasiga tegishlimi yoki yoqmi tekshirish uchun kerak
     * @return List<Attachment> qaytaradi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/getAllByToDivision")
    //Swagger uchun
    @Operation(description = "Boshqarmaga kelgan fayllar ro'yxatini olish uchun ishlatiladi)")
    public List<Attachment> getAllByToDivision(@CurrentUser Users users) {
        return attachmentService.getAllByToDivision(users);
    }

    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * @CurrentUser Sistemaga kirgan userni olish uchun kerak
     * Boshqarmadan ketgan fayllar ro'yxatini olish uchun ishlatiladi
     * @param users Boshqarmaga kelgan fayllar ro'yxatini olish uchun ishlatiladi
     * @return List<Attachment> qaytaradi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/getAllByFromDivision")
    //Swagger uchun
    @Operation(description = "Boshqarmadan ketgan fayllar ro'yxatini olish uchun ishlatiladi)")
    public List<Attachment> getAllByFromDivision(@CurrentUser Users users) {
        return attachmentService.getAllByFromDivision(users);
    }

    /**
     * Bitta fayl ma'lumotlarini olish uchun ishlatiladi
     * @param users fayl userning boshqarmasiga tegilshliligini tekshiradi
     * @param id fayl id si keladi
     * @return Attachment (fayl ma'lumotlari qaytadi)
     */


    @RoleniTekshirish(role = "USER")
    @GetMapping("/getOneTo/{id}")
    //Swagger uchun
    @Operation(description = "Bitta kelgan faylni olish uchun ishlatiladi")
    public Attachment getOneTo(@CurrentUser Users users, @PathVariable Long id) {
        return attachmentService.getOneTo(users, id);
    }

    /**
     * Bitta fayl ma'lumotlarini olish uchun ishlatiladi
     * @param users fayl userning boshqarmasiga tegilshliligini tekshiradi
     * @param id fayl id si keladi
     * @return Attachment (fayl ma'lumotlari qaytadi)
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/getOneFrom/{id}")
    //Swagger uchun
    @Operation(description = "Bitta ketgan faylni olish uchun ishlatiladi")
    public Attachment getOneFrom(@CurrentUser Users users, @PathVariable Long id) {
        return attachmentService.getOneFrom(users, id);
    }

    /**
     * Fayl ko'rilganligini yoki ko'rilmaganligini beradi (textni jirniy yoki oddiy qilish uchun)
     * @param id fayl id si keladi
     * @return ApiResult orqali natija qaytadi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/setView/{id}")
    //Swagger uchun
    @Operation(description = "Fayl ko'rilganligini yoki ko'rilmaganligini beradi (textni jirniy yoki oddiy qilish uchun)")
    public HttpEntity<?> setView(@PathVariable Long id, @CurrentUser Users users) {
        ApiResult apiResult = attachmentService.setView(id, users);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * Fayl kelgani to'g'risida tasdiq beradi
     * @param id fayl id si keladi
     * @return ApiResult orqali natija qaytadi
     */

    @RoleniTekshirish(role = "USER")
    @GetMapping("/setPDTV/{id}")
    //Swagger uchun
    @Operation(description = "Fayl kelgani to'g'risida tasdiq beradi")
    public HttpEntity<?> setPDTV(@PathVariable Long id, @CurrentUser Users users) {
        ApiResult apiResult = attachmentService.setPDTV(id, users);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * Fayl o'chiradi
     * @param id fayl id si keladi
     * @return ApiResult orqali natija qaytadi
     */

    @RoleniTekshirish(role = "USER")
    @DeleteMapping("/deleteOneTo/{id}")
    //Swagger uchun
    @Operation(description = "Fayl o'chiradi)")
    public HttpEntity<?> deleteOneTo(@CurrentUser Users users, @PathVariable Long id) {
        ApiResult apiResult = attachmentService.deleteOneTo(users, id);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }




}
