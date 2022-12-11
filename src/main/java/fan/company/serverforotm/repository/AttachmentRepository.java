package fan.company.serverforotm.repository;

import fan.company.serverforotm.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {


    List<Attachment> findAllByToDivisionId(Long toDivision_id);
    List<Attachment> findAllByFromDivisionId(Long fromDivision_id);
}
