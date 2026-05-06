package donaton.apigateway3.config;

import donaton.apigateway3.filter.FirebaseAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FirebaseAuthFilter firebaseAuthFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firebaseAuthFilter)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/auth/**");
    }
}