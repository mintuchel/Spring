package mvc2.exception.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

// 등록을 안함
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("client request [Dispatcher Type : {}] [URI : {}]", request.getDispatcherType(), requestURI);
        }catch(Exception e){
            throw e;
        }finally{
            log.info("response [Dispatcher Type : {}] [URI : {}]", request.getDispatcherType(), requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}