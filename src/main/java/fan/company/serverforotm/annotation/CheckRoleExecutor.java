package fan.company.serverforotm.annotation;

import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.exceptions.ForbiddenException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckRoleExecutor {

    /**
     * Annatatsiya qachon ishlashini belgilaydi
     *
     * foydalanuvchi rolini tekshirish uchun annatation
     */

    @Before(value = "@annotation(roleniTekshirish)")
    public void roleniTekshirish(RoleniTekshirish roleniTekshirish) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!roleniTekshirish.role().contains(user.getRole().getName()))
            throw new ForbiddenException(roleniTekshirish.role(), "Ruxsat yo'q!");



    }

}
