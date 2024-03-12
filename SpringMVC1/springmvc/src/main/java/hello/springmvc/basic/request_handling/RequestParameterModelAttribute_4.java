package hello.springmvc.basic.request_handling;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 3번에서는 URI 의 뒷부분에 request parameter 로 온 data 들을
// @RequestParam 로 하나씩 추출했었다.

// 하지만 다룰 것이 객체일 경우에는 추출하고 대입하는게 번거롭다.

// @ModelAttribute 를 사용하면 편리하게 한번에 객체로 바인딩하여 받을 수 있다.

@Slf4j
@RestController
public class RequestParameterModelAttribute_4 {

    // @RequestParam 으로 객체에 대한 data 를 받을때면
    // 이렇게 노가다로 추출하고, 노가다로 다시 객체에 대입해야한다.
    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@RequestParam(defaultValue = "messi") String username,
                                   @RequestParam(defaultValue = "10") int age){
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return helloData.toString();
    }

    // @ModelAttribute 를 사용하면 바로 객체로 받을 수 있다
    // @ModelAttribute 를 통해 request parameter 들을 자동으로 객체에 바인딩하면 된다
    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    public String modelAttributeV2(@ModelAttribute HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return helloData.toString();
    }
}
