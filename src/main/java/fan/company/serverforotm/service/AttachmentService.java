package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.Attachment;
import fan.company.serverforotm.entity.Division;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.exceptions.ResourceNotFoundException;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.AttachmentDto;
import fan.company.serverforotm.repository.AttachmentRepository;
import fan.company.serverforotm.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    AttachmentStatisticService attachmentStatisticService;
    @Autowired
    BaseUpdateService baseUpdateService;

    private static final String uploadFile = "YuklanganFayllar";


    public ApiResult uploadFileToFileSystem(Users user, AttachmentDto dto, MultipartHttpServletRequest request) throws IOException {

        try {

            Iterator<String> fileNames = request.getFileNames();
            List<String> idlar = new ArrayList<>();

            while (fileNames.hasNext()) {

                MultipartFile file = request.getFile(fileNames.next());
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    Attachment attachment = new Attachment();
                    attachment.setOriginalName(originalFilename);
                    attachment.setSize(file.getSize());
                    attachment.setContentType(file.getContentType());
                    String[] split = originalFilename.split("\\.");
                    String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
                    attachment.setName(name);
                    attachment.setFromDivision(divisionRepository.findById(dto.getFromDivision()).orElseThrow(() -> new ResourceNotFoundException("division", "id", dto.getFromDivision())));
                    attachment.setToDivision(divisionRepository.findById(dto.getToDivision()).orElseThrow(() -> new ResourceNotFoundException("division", "id", dto.getToDivision())));
                    Attachment savedAttachment = attachmentRepository.save(attachment);
                    File path = new File(uploadFile);
                    path.mkdir();
                    path = new File(path.getPath() + "/" + name);
                    Files.copy(file.getInputStream(), Path.of(path.getPath()));
                    idlar.add(savedAttachment.getId().toString());
                    attachmentStatisticService.add(savedAttachment);

                }

            }

            baseUpdateService.editUpdated(dto.getToDivision(), true);

            return new ApiResult("Fayl saqlandi" + idlar, true, attachmentRepository.findAllByFromDivisionId(user.getDivision().getId()));

        } catch (Exception e) {
            return new ApiResult("Xatolik yuz berdi", false);
        }
    }

    public ApiResult downloadFromFileSystem(Users user, Long id, HttpServletResponse response) {

        try {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
            if (!optionalAttachment.isPresent())
                return new ApiResult("Bunday idlik fayl yo'q", false);

            if (user.getDivision().getId() != optionalAttachment.get().getToDivision().getId())
                return new ApiResult("Bunday huquq yo'q!", false);

            Attachment attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + attachment.getOriginalName() + "\"");
            response.setContentType(attachment.getContentType());

            FileInputStream fileInputStream = new FileInputStream(uploadFile + "/" + attachment.getName());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
            return new ApiResult("Fayl yuklandi", true);

        } catch (Exception e) {
            return new ApiResult("Xatolik yuz berdi", false);
        }
    }


    public List<Attachment> getAllByToDivision(Users users) {

        try {
            baseUpdateService.editUpdated(users.getDivision().getId(), false);
            return attachmentRepository.findAllByToDivisionId(users.getDivision().getId());
        } catch (Exception e) {
//            log.error(e.getMessage());
            throw new ResourceNotFoundException("user", "id", users.getDivision().getId());
        }
    }

    public List<Attachment> getAllByFromDivision(Users users) {

        try {
            return attachmentRepository.findAllByFromDivisionId(users.getDivision().getId());
        } catch (Exception e) {
//            log.error(e.getMessage());
            throw new ResourceNotFoundException("user", "id", users.getDivision().getId());
        }

    }


    public Attachment getOneTo(Users users, Long id) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new Attachment();

        if (users.getDivision().getId() != optionalAttachment.get().getToDivision().getId())
            throw new ResourceNotFoundException("user", "id", optionalAttachment.get().getToDivision().getId());


        return optionalAttachment.get();
    }

    public Attachment getOneFrom(Users users, Long id) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new Attachment();

        if (users.getDivision().getId() != optionalAttachment.get().getFromDivision().getId())
            throw new ResourceNotFoundException("user", "id", optionalAttachment.get().getFromDivision().getId());


        return optionalAttachment.get();
    }

    public ApiResult setView(Long id, Users users) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new ApiResult("Bunday idlik fayl yo'q", false);

        Attachment attachment = optionalAttachment.get();
        attachment.setView(true);
        attachmentRepository.save(attachment);
        return new ApiResult("SUCCESS", true, attachmentRepository.findAllByToDivisionId(users.getDivision().getId()));

    }

    public ApiResult setPDTV(Long id, Users users) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new ApiResult("Bunday idlik fayl yo'q", false);

        Attachment attachment = optionalAttachment.get();
        attachment.setPdtv(true);
        Attachment saved = attachmentRepository.save(attachment);
        attachmentStatisticService.setPDTV(saved.getId(), users);
        return new ApiResult("SUCCESS", true, attachmentRepository.findAllByToDivisionId(users.getDivision().getId()));

    }

    public ApiResult deleteOneTo(Users users, Long id) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new ApiResult("Bunday idlik fayl yo'q", false);

        if (users.getDivision().getId() != optionalAttachment.get().getToDivision().getId())
            throw new ResourceNotFoundException("user", "id", optionalAttachment.get().getToDivision().getId());

        File file = new File(uploadFile + "/" + optionalAttachment.get().getName());

        if (file.delete()) {
            attachmentRepository.deleteById(optionalAttachment.get().getId());
            return new ApiResult("DELETED", true, attachmentRepository.findAllByToDivisionId(users.getDivision().getId()));
        }
        return new ApiResult("O'chirishda xatolik", false);
    }

}
