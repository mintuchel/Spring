package hello.login.web.spring_interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    // Controller 까지 가기 전에 호출
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // request 에서 URL 추출
        String requestURI = request.getRequestURI();
        // 요청 로그를 구분하기 위한 random 한 UUID 생성
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    // Controller 호출 이후에 호출
    // 그래서 인자로 ModelAndView 가 있다!!
    // 예외가 발생하면 호출되지 않음!!
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    // viewResolver 로 인해 완성된 view 가 만들어진 후에 호출됨
    // finally 구문처럼 언제나 호출됨
    // 예외가 발생했던 안했던 항상 호출
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}]", logId, requestURI);
        if(ex!=null){
            log.error("afterCompletion error", ex);
        }
    }
}
