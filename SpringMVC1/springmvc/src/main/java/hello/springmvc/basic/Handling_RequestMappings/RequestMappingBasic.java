package hello.springmvc.basic.Handling_RequestMappings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestMappingBasic {

    private Logger log = LoggerFactory.getLogger(getClass());

    // 기본 매핑
    // /hello-basic 이 들어오면 해당 메서드를 실행한다
    // 두번째 인자를 지정하지 않았으니 모든 HTTP method 허용 : GET, POST, PUT, PATCH, DELETE
    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    // 특정 HTTP method 요청만 허용하기
    // RequestMapping 에 HTTP Method 설정 가능

    // /mapping-get-v1 이고 GET 방식일때 이 함수를 호출
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    // HTTP Method 축약
    // @RequestMapping 을 축약해서 쓸 수 있다
    // @GetMapping
    // @PostMapping
    // @PutMapping
    // @DeleteMapping
    // @PatchMapping

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "ok";
    }

    // 최근 HTTP API 는 아래와 같이 URI 를 템플릿화하는 방식을 선호함
    // 따라서 URI 경로를 템플릿화 해서 변수값을 추출해서 사용하는 방법을 많이 씀.
    // PathVariable 을 사용할때는 template 된 변수값 그대로 써주면 됨.

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId = {}", data);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }
}