package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 전형적인 방식

// ModelAndView 를 사용하는 대신 Model 을 사용하는 예시 (훨씬 좋은 방법)
// 그리고 String 으로 ViewResolver 가 찾아야할 view name 을 반환하는 예시이다.

@Controller
public class ResponseByViewName {

    @RequestMapping("/response-view-v1")
    public String responseView1(Model model){
        model.addAttribute("data", "hello");
        return "response/hello";
    }

}