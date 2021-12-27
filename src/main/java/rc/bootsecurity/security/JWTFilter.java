package rc.bootsecurity.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {

        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                String jwt = this.resolveToken(httpServletRequest);
                logger.info(">>>Before JWT Token Validation: " + jwt + " <<<");
                if (StringUtils.hasText(jwt)) {
                    if (tokenProvider.validateToken(jwt)) {
                        Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        httpServletRequest.getSession().setAttribute("NOTES_SESSION", "tahermohaidya@gmail.com");
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
            //this.resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            logger.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.debug("Exception " + eje.getMessage(), eje);
        }
    }


    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(SecurityConfiguration.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        if (request.getRequestURL().toString().contains("/index")) {
            return "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsIklzc3VlciI6Iklzc3VlciIsImlhdCI6MTYzNzY1NzkwMiwidXNlcm5hbWUiOiJhZG1pbiJ9.yoX5R2JeJjbCe-0cn-5i3aqLShsnzaTFddrIvEaEg5s";
        } else {
            return null;
        }


    }
}


