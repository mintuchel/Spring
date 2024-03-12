package hello.servlet.understanding_web.SpringMVC.v3;


import hello.servlet.Classes.Member;
import hello.servlet.Classes.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// v2 에서는 ModelAndView 를 넘겼는데 여기서는 viewName 만 반환한다
// 이래도 되는게 annotation 기반 controller 는 ModelAndView 뿐만 아니라 그냥 viewName 만 반환해도 동작하기 때문이다.
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value="/new-form", method=RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }

    //@RequestMapping(value="/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username")String username,
                       @RequestParam("age")int age,
                       Model model){
        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member",member);
        return "save-result";
    }

    // @RequestMapping(method=RequestMethod.GET)
    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "members";
    }
}
