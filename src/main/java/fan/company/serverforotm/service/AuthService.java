package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.LoginDto;
import fan.company.serverforotm.repository.RoleRepository;
import fan.company.serverforotm.repository.UserRepository;
import fan.company.serverforotm.security.tokenGenerator.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResult login(LoginDto dto) {

        try {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(),
                    dto.getPassword()
            ));
            Users user = (Users) authenticate.getPrincipal();
            String token = jwtProvider.generatorToken(user.getUsername(), user.getRole());
            return new ApiResult("Token", true, token, user.getRole());

        } catch (BadCredentialsException e){
            return new ApiResult("Login yoki parol xato!", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * Usernameni orqali topish
         */

        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " topilmadi!"));
    }


}
