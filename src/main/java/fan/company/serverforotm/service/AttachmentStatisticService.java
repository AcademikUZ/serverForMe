package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.Attachment;
import fan.company.serverforotm.entity.AttachmentStatistic;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.payload.TimestampDto;
import fan.company.serverforotm.repository.AttachmentStatisticRepository;
import fan.company.serverforotm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentStatisticService {

    @Autowired
    AttachmentStatisticRepository attachmentStatisticRepository;
    @Autowired
    UserRepository userRepository;

    public void add(Attachment dto) {

        AttachmentStatistic statistic = new AttachmentStatistic(
                dto.getName(),
                dto.getOriginalName(),
                dto.getSize(),
                dto.getContentType(),
                dto.getToDivision(),
                dto.getFromDivision(),
                dto.getId()
        );

        attachmentStatisticRepository.save(statistic);
    }

    public void setPDTV(Long attachmendId, Users user){
        Optional<AttachmentStatistic> optionalAttachmentStatistic = attachmentStatisticRepository.findByAttachmentId(attachmendId);

        AttachmentStatistic statistic = optionalAttachmentStatistic.get();

        statistic.setPdtvTime(new Timestamp(System.currentTimeMillis()));
        statistic.setPdtvUser(user);

        attachmentStatisticRepository.save(statistic);

    }

    public List<AttachmentStatistic> getAllByCreatedAtBetween(TimestampDto dto){
        return attachmentStatisticRepository.findAllByCreatedAtBetween(dto.getStart(), dto.getEnd());
    }

    public List<AttachmentStatistic> getAllByCreatedAtBetweenFromDivision(TimestampDto dto){
        return attachmentStatisticRepository.findAllByCreatedAtBetweenAndFromDivisionId(dto.getStart(), dto.getEnd(), dto.getDivisionId());
    }

    public List<AttachmentStatistic> getAllByCreatedAtBetweenToDivision(TimestampDto dto){
        return attachmentStatisticRepository.findAllByCreatedAtBetweenAndToDivisionId(dto.getStart(), dto.getEnd(), dto.getDivisionId());
    }



}
