package hello.springmvc.basic.request_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

// 이 패키지에서는 특정 작업을 하는 것보다는 Client 가 보낸 request 를 분석하는 작업을 한다.
// Client 가 request 를 보냈을때 해당 request 의 header 에는 어떤 것들이 있는지 알아보자

// @RequestHeader
// HTTP Request 의 특정 header 정보를 조회할 수 있게 만든 annotation 이다.

@Slf4j
@RestController
public class RequestHeader_1 {

    // 인자로 굉장히 많이 받는다
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @org.springframework.web.bind.annotation.RequestHeader MultiValueMap<String, String> headerMap,
                          @org.springframework.web.bind.annotation.RequestHeader("host") String host,
                          @CookieValue(value="myCookie", required = false) String cookie){

        log.info("request = {}", request);
        log.info("response = {}", response);
        log.info("httpMethod = {}", httpMethod);
        log.info("locale = {}", locale);
        log.info("headerMap = {}", headerMap);
        log.info("host = {}", host);
        log.info("cookie = {}", cookie);

        return "ok";
    }
}
