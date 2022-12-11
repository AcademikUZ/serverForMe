package fan.company.serverforotm.annotation;

import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.exceptions.ForbiddenException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckPermissionExecutor {

    /**
     * Annatatsiya qachon ishlashini belgilaydi
     *
     * Permission yani foydalanuvchi huquqlarini tekshirish uchun annatation
     */

    @Before(value = "@annotation(huquqniTekshirish)")
    public void huquqniTekshirish(HuquqniTekshirish huquqniTekshirish) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exist = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(huquqniTekshirish.huquq())) {
                exist = true;
                break;
            }
        }
        if(!exist)
            throw new ForbiddenException(huquqniTekshirish.huquq(), "Ruxsat yo'q!");



    }

}
