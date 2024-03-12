package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller;

import hello.servlet.Classes.Member;
import hello.servlet.Classes.MemberRepository;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    public ModelView process(Map<String, String> paramMap){
        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
    }
}
