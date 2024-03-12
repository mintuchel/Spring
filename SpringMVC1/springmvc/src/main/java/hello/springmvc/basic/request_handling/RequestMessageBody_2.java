package hello.springmvc.basic.request_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

// JSON 같이 message body 로 데이터를 보낼때
// HTTP REQUEST BODY 에서 데이터를 추출하는 방법을 배워본다.

// 이건 3번의 Request parameter 와는 다른 얘기다.
// 얘는 request parameter 로 data 를 보내는게 아닌,
// HTTP Request 의 message body 에 data 를 담아 보내는 것이기 때문이다.

// Http message Body 에 데이터를 직접 담아서 오는 경우에는 @RequestParam 과 @ModelAttribute 를 사용하지 못한다!

// request parameter 로 보낼때는 @RequestParam, @ModelAttribute 로
// message body 로 보낼때는 HttpEntity, @RequestBody 로 data 를 추출해주면 된다!

@Slf4j
@Controller
public class RequestMessageBody_2 {

    // 1. @RequestBody 사용하지 않을때
    // HttpEntity 객체로 받아 getBody 로 메세지 바디 정보를 직접 조회 가능
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity){
        log.info("messageBody={}", httpEntity.getBody());
        return new HttpEntity<>("ok");
    }

    // 2. @RequestBody 사용
    // Http Message Body 정보를 편리하게 조회하게 해주는 annotation
    // Body 가 아닌 header 정보가 필요하면 @RequestHeader 를 사용해주면 된다.
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String body){
        log.info("messageBody={}", body);
        return "ok";
    }
}
