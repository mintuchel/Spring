package hello.servlet.understanding_web.SpringMVC.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
// 원래 frontcontroller 는 extends HttpServlet 을 하고 @WebServlet 으로 urlPattern 을 넣어줌
// 근데 이제는 annotation 을 써주어 전에 했던 것처럼 안해도 된다
public class SpringMemberFormControllerV1 {
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        // 논리 이름 넘기면 properties 에서 명시해놓은대로 prefix suffix 추가해서 전달함
        return new ModelAndView("new-form");
    }
}