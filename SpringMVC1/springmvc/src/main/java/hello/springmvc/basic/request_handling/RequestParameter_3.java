package hello.springmvc.basic.request_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
request parameter 란 URI 의 끝에 나열된 key-value 쌍들이다
만약 HTTP Request message 에서 data 가 request parameter 로 전달되면
다음과 같이 @RequestParam 을 사용해서 data 들을 바로 추출하여 사용할 수 있다
 */

@Slf4j
@Controller
public class RequestParameter_3 {

    // Client 가 보낸 request 를 통째로 받지 말고 "필요한 데이터들만 추출해서 받아보자!"

    // 매개변수에 @RequestParam 사용해서 바로 추출하여 받을 수 있다
    // @ResponseBody : view resolver 과정을 생략하고 바로 HTTP message body 에 직접 해당 내용을 입력

    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam String memberName, @RequestParam int memberAge){
        log.info("username={}, age={}", memberName, memberAge);

        // @ResponseBody 덕에 web 화면에 "ok"가 출력된다
        return "ok";
    }

    // required 변수로 필수 여부를 나타낼 수 있다
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required=true) String username,
                                       @RequestParam(required=false) Integer age){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(defaultValue="messi") String username,
                                      @RequestParam(defaultValue="10") int age){
        log.info("username={}, age={}", username, age);
        return "ok";
    }
}