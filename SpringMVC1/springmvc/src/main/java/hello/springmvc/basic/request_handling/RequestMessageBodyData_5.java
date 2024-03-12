package hello.springmvc.basic.request_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// messageBody 에 텍스트 형식으로 데이터가 들어갔으면 여기 있는 방법들을 사용할 수 있다.

// request parameter 와는 다르게 URI 끝에 딸려 오는 것이 아닌,
// HTTP message body 를 통해 데이터가 직접 넘어온 경우는
// @RequestParam @ModelAttribute 를 사용할 수 없다!!

// 따라서 다른 방식을 사용하여 데이터를 추출해야한다

// 당연히 아래로 갈수록 더 좋은 버전이다.

// @RequestBody
// 이걸 사용하면 HTTP message body 를 편리하게 조회할 수 있다.
// 참고로 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader를 사용하면 된다.
// 이렇게 "message body" 를 직접 조회하는 기능은 request parameter 를 조회하는
// @RequestParam @ModelAttribute 와는 전혀 상관이 없다!

// @ResponseBody
// 응답결과를 HTTP message body 에 직접 담아서 전달할 수 있다.
// 이 경우에는 추후에 DispatcherServlet 이 ViewResolver 를 거치지 않는다.

@Slf4j
@Controller
public class RequestMessageBodyData_5 {

    // 구식버전이다.
    // HttpServlet 을 인자로 받는건 그냥 다 구식버전이라고 보면 된다
    // Spring 쓰는데 굳이 Servlet 을 통째로 받을 필요가 없다
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    // HttpEntity 를 통해 messageBody 를 한번에 객체로 받을 수 있다.
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException{
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new HttpEntity<>("ok");
    }

    // RequestEntity 로 받을 수 도 있다.
    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV4(RequestEntity<String> httpEntity) throws IOException{
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

    // 요청오는건 @RequestBody 로 응답나가는건 @ResponseBody 로 해주기
    // 이 방법을 실무에서 엄청 많이 쓴다
    @ResponseBody
    @PostMapping("/request-body-string-v6")
    public String requestBodyStringV6(@RequestBody String messageBody){
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
