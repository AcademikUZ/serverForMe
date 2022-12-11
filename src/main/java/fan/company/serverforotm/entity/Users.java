package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.enums.Huquq;
import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Users extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Division division;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public Users(String fullName, String username, String password, Role role, Division division) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.division = division;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Huquq> huquqList = this.role.getHuquqList();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Huquq huquq : huquqList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(huquq.name()));
        }
        return grantedAuthorities;
    }
}
