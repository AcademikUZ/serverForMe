package fan.company.serverforotm.security.audit;


import fan.company.serverforotm.entity.Users;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class KimYozganiniAuditQilish implements AuditorAware<Long> {


    /**
     * Audit qilishuchun zaruriy sozlamalar Long bu userni id si qaysi tipda ekanligi
     * agar UUID bo'lsa UUID ishlatiladi
     *
     * AuditorAware implements qilish kerak
     * @return
     */


    @Override
    public Optional<Long> getCurrentAuditor() {

        // Sistemaga kirgan userni olish uchun ishlatiladi

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Userni idsi qaytadi

        if(        authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")){
            return Optional.of(((Users) authentication.getPrincipal()).getId());
        }

        return Optional.empty();
    }
}
