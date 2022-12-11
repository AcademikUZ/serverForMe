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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@Tag(name = "statistics", description = "Fayllarni statistikasi uchun")
public class StatisticsController {

    @Autowired
    AttachmentStatisticService service;

    /**
     * @return
     * @Valid validatsiyadan o'tadi, ya'ni json orqali to'liq ma'lumot kelganligini tekshiradi
     * @RoleniTekshirish rolni tekshiradi
     * Faqat admin ko'ra oladi
     * Vaqt oraligi'dagi sistemaga yuklangan fayllar ro'yxatini ko'radi
     */

    private Timestamp stringToDate(String str) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(str);
            Timestamp timestamp = new Timestamp(date.getTime());
            System.out.println(timestamp);
            return timestamp;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @RoleniTekshirish(role = "ADMIN")
    @GetMapping("/getallbycreatedatbetween")
    public HttpEntity<?> getAllByCreatedAtBetween(@Valid @RequestParam String start, String end) {

        TimestampDto timestampDto = new TimestampDto();
        timestampDto.setStart(stringToDate(start));
        timestampDto.setEnd(stringToDate(end));

        List<AttachmentStatistic> list = service.getAllByCreatedAtBetween(timestampDto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

    /**
     * Vaqt oraligi'dagi sistemaga yuklangan Yuborilgan fayllar ro'yxatini ko'radi
     * User va admin ko'ra oladi
     *
     * @return
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/getallbycreatedatbetweenfromdivision")
    public HttpEntity<?> getAllByCreatedAtBetweenFromDivision(@RequestParam String start, String end, String divisionId) {

        TimestampDto dto = new TimestampDto();
        dto.setStart(stringToDate(start));
        dto.setEnd(stringToDate(end));
        dto.setDivisionId(Long.valueOf(divisionId));

        List<AttachmentStatistic> list = service.getAllByCreatedAtBetweenFromDivision(dto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

    /**
     * Vaqt oraligi'dagi sistemaga yuklangan qabul qilingan fayllar ro'yxatini ko'radi
     * User va admin ko'ra oladi
     *
     * @return
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/getallbycreatedatbetweentodivision")
    public HttpEntity<?> getAllByCreatedAtBetweenToDivision(@RequestParam String start, String end, String divisionId) {

        TimestampDto dto = new TimestampDto();
        dto.setStart(stringToDate(start));
        dto.setEnd(stringToDate(end));
        dto.setDivisionId(Long.valueOf(divisionId));


        List<AttachmentStatistic> list = service.getAllByCreatedAtBetweenToDivision(dto);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.CONFLICT : HttpStatus.OK).body(list);
    }

}
