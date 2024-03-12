package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 이제는 Console 창이나 web 에 메세지를 띄워 확인하지 않고
// log 로 확인한다
// @Slf4j lombok 을 사용하면 더 쉽다
@RestController
public class LogExample {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log ={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}