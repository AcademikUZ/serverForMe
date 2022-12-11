package fan.company.serverforotm.security.config;

import fan.company.serverforotm.security.PasswordValidator;
import fan.company.serverforotm.security.filter.JwtFilter;
import fan.company.serverforotm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuryConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()   // "/api/auth/**" yo'ini barchaga ochib qo'yadi
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //filterni o'rnatish uchun kerak
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //har safar tokenni tekshirish
        http.cors(); // Cors uchun kerak
    }

    /**
     * Buni WebSecurityConfigurerAdapter ni o'rniga ishlatadi
     */

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                        .csrf().disable()
//                        .httpBasic().disable()
//                        .authorizeRequests()
//                        .antMatchers("/api/auth/register").permitAll()
//                        .anyRequest().authenticated();
//
//        return http.build();
//    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * Usernameni username orqali topish
         */

        auth
                .userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    /**
     * bazada parol ochiq saqlanmasligi uchun passwordEncoder ishlatiladi
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Passwordni mustahkamligini tekshiradi
     */

    @Bean
    PasswordValidator passwordValidator(){
        return new PasswordValidator();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Cors sozlamalari
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowedHeaders("*");
                .allowedMethods("*");
//                .allowCredentials(true);
    }

}
