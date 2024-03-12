package hello.springmvc.basic.request_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

// 여기에는 없는 문자로 변환하고 다시 json 객체로 변환하는 과정이 매우 불편하다.
// 따라서 바로 한번에 json 데이터를 객체로 받을 수 있는 방법이 존재한다.

// 우선 request 로 들어온 json data 를 java 객체로 변환해주고
// response 할때도 java 객체를 json data 로 변환해주는 ObjectMapper 를 사용해야한다.

// HttpEntity @RequestBody 를 사용하면 HTTP 메세지 컨버터가 HTTP 메세지 바디의
// 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
// HTTP 메시지 컨버터는 문자 뿐만 아니라 json 도 객체로 변환해주는데,
// 이를 통해 우리가 바로 객체로 받을 수 있는 것이다.

// 스프링은 @ModelAttribute @RequestParam 생략 시 다음과 같은 규칙을 적용한다
// String, Int, Integer 같으 단순 타입 = @RequestParam
// 나머지 = @ModelAttribute

@Slf4j
@Controller
public class RequestJSON_6 {
    private ObjectMapper objectMapper = new ObjectMapper();

    // @RequestBody 로 받을 수 있다
    // json 을 받을때는 @RequestBody 는 생략불가이다
    @ResponseBody
    @PostMapping("/request-body-json-v1")
    public String requestBodyJSONV1(@RequestBody HelloData inputdata) throws IOException {
        log.info("username={}, age={}", inputdata.getUsername(), inputdata.getAge());
        return "ok";
    }

    // HttpEntity 로도 받을 수 있다

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJSONV2(HttpEntity<HelloData> inputdata){
        HelloData data = inputdata.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data){
        log.info("username={}, age={}",data.getUsername(), data.getAge());
        return data;
    }
}