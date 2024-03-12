package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    public String home(){
        return "home";
    }

    // client 는 요청시 항상 SessionID 쿠키를 전달
    // 즉 http request 에 sessionID 가 항상 박혀있음!
    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required=false) Member loginMember, Model model){

        // login 실패시 그냥 home html 보여주기
        if(loginMember == null) return "home";

        // login 성공 시(session 이 유지되면) loginHome html 반환
        // client 에게 사용자 전용 홈 화면인 loginHome html 을 보여주기
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}