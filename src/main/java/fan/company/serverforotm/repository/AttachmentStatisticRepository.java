package fan.company.serverforotm.repository;

import fan.company.serverforotm.entity.Attachment;
import fan.company.serverforotm.entity.AttachmentStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface AttachmentStatisticRepository extends JpaRepository<AttachmentStatistic, Long> {

    @Query("select a from AttachmentStatistic a where a.createdAt between ?1 and ?2")
    List<AttachmentStatistic> findAllByCreatedAtBetween(Timestamp start, Timestamp end);

    List<AttachmentStatistic> findAllByCreatedAtBetweenAndFromDivisionId(Timestamp start, Timestamp end, Long fromDivision_id);

    List<AttachmentStatistic> findAllByCreatedAtBetweenAndToDivisionId(Timestamp start, Timestamp end, Long toDivision_id);

    Optional<AttachmentStatistic> findByAttachmentId(Long attachmentId);

}
