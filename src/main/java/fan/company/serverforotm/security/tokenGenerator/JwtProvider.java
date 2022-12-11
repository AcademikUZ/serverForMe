package fan.company.serverforotm.security.tokenGenerator;

import fan.company.serverforotm.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtProvider {

    private String keyForToken = "StrongPasswordForToken-290889";  // token kaliti
    private long expireTime = 1000 * 60 * 60 * 24; //tokenni amal qilish muddati | 1 kun


    /**
     * token generatsiya qiladi username + role + keyForToken orqali
     * @param username
     * @param role
     * @return
     */
    public String generatorToken(String username, Role role) {

        Date expiteDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiteDate)
                .claim("roles", role.getName())
                .signWith(SignatureAlgorithm.HS512, keyForToken)
                .compact();
        return token;

    }

    /**
     * Token ichidan usernameni olib beradi
     * @param token
     * @return
     */

    public String getUsernameFromToken(String token) {
        try {

            String username = Jwts
                    .parser()
                    .setSigningKey(keyForToken)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return username;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }


}
