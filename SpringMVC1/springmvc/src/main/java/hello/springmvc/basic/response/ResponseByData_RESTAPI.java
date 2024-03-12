package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
// @RestController = @Controller + @ResponseBody

// @ResponseBody 를 달면 DispatcherServlet 은 ViewResolver 를 호출하는 과정을 거치지 않는다.
// Controller 에서 받은 객체를 그대로 HTTP message body 에 넣고 Client 에게 보낸다.

public class ResponseByData_RESTAPI {

    // ResponseEntity 객체로 보내기
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    // @ResponseBody 사용해서 보내기
    // 그냥 return 을 바로 객체로 해주면 된다
    // @ResponseStatus 는 ResponseEntity 처럼 못하기 때문에 붙인 어노테이션이다
    // 보통 RESTAPI 쓰는 곳이 이렇게 사용한다.
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}