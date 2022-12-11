package fan.company.serverforotm.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import fan.company.serverforotm.security.tokenGenerator.JwtProvider;
import fan.company.serverforotm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthService authService;

    /**
     * Sistemaga kirishdan oldin userni tokenini tekshirib agar token yaxshi bo'lsa userni sistemaga kiritib yuboradi
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String authorization = request.getHeader("Authorization");

            System.out.println(request.getMethod());
            if (authorization != null && authorization.startsWith("Bearer")) {
                authorization = authorization.substring(7);
                String usernameFromToken = jwtProvider.getUsernameFromToken(authorization);
                if (usernameFromToken != null) {
                    UserDetails userDetails = authService.loadUserByUsername(usernameFromToken);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
        
    }
}
