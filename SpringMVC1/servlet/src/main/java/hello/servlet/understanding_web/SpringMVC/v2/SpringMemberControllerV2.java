package hello.servlet.understanding_web.SpringMVC.v2;

import hello.servlet.Classes.Member;
import hello.servlet.Classes.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
// 클래스 위에 써놓은 RequestMapping 과 
// 팀 아래에 써놓는 RequestMapping 은 조합이 된다
// 그래서 /springmvc/v1/members 는 무조건 들어가게 된다
@RequestMapping("/springmvc/v1/members")
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("new-form")
    public ModelAndView newForm(){
        return new ModelAndView("new-form");
    }

    @RequestMapping("save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        Member save = memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member",member);
        return mv;
    }

    @RequestMapping
    public ModelAndView members(){
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members",members);

        return mv;
    }
}
