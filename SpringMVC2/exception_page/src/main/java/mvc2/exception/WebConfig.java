package mvc2.exception;

import mvc2.exception.mvc_interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// error 에 대한 request 가 왔을때는
// 인터셉터 중복호출을 방지해야한다
// error 에 대한 내부 request 에도 호출된다면 두 번 호출되는 것으로 매우 비효율적이기 때문!
// 오류 페이지일때는 해당 interceptor 적용하지 않게 만들어주면 된다
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 정의한 LogInterceptor 등록
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*.ico",
                        "/error", "/error-page/**");
    }
}