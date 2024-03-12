package hello.login.web.spring_interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// login check 는 Controller 호출 전에만 하면 됨
// 그래서 postHandle 이 필요가 없음!
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("login check interceptor {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("아직 로그인 안된 사용자임");

            response.sendRedirect("/login?redirectURL=" + requestURI);
            // 로그인 안됬으면 Controller 로 안보냄
            // 그 전에 빠꾸처리함
            return false;
        }

        return true;
    }
}
