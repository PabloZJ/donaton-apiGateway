package donaton.apigateway3.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FirebaseAuthFilter implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        if (path.startsWith("/auth/")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            request.setAttribute("uid", decodedToken.getUid());
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}