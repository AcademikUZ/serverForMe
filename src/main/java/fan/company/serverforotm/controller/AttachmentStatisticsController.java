package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.entity.AttachmentStatistic;
import fan.company.serverforotm.payload.TimestampDto;
import fan.company.serverforotm.service.AttachmentStatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attachmentStatistics")
@Tag(name = "AttachmentStatisticsController", description = "Fayllarni statistikasi uchun")
public class AttachmentStatisticsController {

    @Autowired
    AttachmentStatisticService service;

    /**
     * @Valid validatsiyadan o'tadi, ya'ni json orqali to'liq ma'lumot kelganligini tekshiradi
     * @RoleniTekshirish rolni tekshiradi
     * Faqat admin ko'ra oladi
     * Vaqt oraligi'dagi sistemaga yuklangan fayllar ro'yxatini ko'radi
     * @param dto vaqt oraligi keladi (Timestamp start, Timestamp end )
     * @return
     */

    @RoleniTekshirish(role = "ADMIN")
    @GetMapping("/getAllByCreatedAtBetween")
    public HttpEntity<?> getAllByCreatedAtBetween(@Valid @RequestBody TimestampDto dto){
        List<AttachmentStatistic> list = service.getAllByCreatedAtBetween(dto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

    /**
     * Vaqt oraligi'dagi sistemaga yuklangan Yuborilgan fayllar ro'yxatini ko'radi
     * User va admin ko'ra oladi
     * @param dto vaqt oraligi keladi (Timestamp start, Timestamp end )
     * @return
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/getAllByCreatedAtBetweenFromDivision")
    public HttpEntity<?> getAllByCreatedAtBetweenFromDivision(@Valid @RequestBody TimestampDto dto){
        List<AttachmentStatistic> list = service.getAllByCreatedAtBetweenFromDivision(dto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

    /**
     * Vaqt oraligi'dagi sistemaga yuklangan qabul qilingan fayllar ro'yxatini ko'radi
     * User va admin ko'ra oladi
     * @param dto vaqt oraligi keladi (Timestamp start, Timestamp end )
     * @return
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/getAllByCreatedAtBetweenToDivision")
    public HttpEntity<?> getAllByCreatedAtBetweenToDivision(@Valid @RequestBody TimestampDto dto){
        List<AttachmentStatistic> list = service.getAllByCreatedAtBetweenToDivision(dto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

}
