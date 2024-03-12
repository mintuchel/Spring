package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    // GET 으로 왔을경우
    // loginForm.html 반환만 해주면 됨
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }

    // client 가 데이터를 입력하여 
    // POST 로 똑같은 URL 로 다시 보냈을 경우
    // login 성공하면 
    // login 실패면 다시 loginForm.html 다시 보여주기
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue="/") String redirectURL, HttpServletRequest request) {

        // FieldError 처리
        // Error 가 존재하면 loginForm.html 다시 보여주기
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        // 로그인 시도 중인 Member 찾기
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // GlobalError 처리 - reject 사용
        if(loginMember==null){
            bindingResult.reject("loginFail","id 또는 pw가 맞지 않습니다");
            return "login/loginForm";
        }

        // login 성공 시
        // 해당 Member 의 session 이 있으면 기존 session 반환
        // 없으면 신규 session 생성
        HttpSession session = request.getSession();

        // session 에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        // login 성공 시 home 화면으로 redirect
        return "redirect:" + redirectURL;
    }

    // logout 시 Server 쪽에서 session 삭제
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }

        // 그리고 home 화면으로 redirect
        return "redirect:/";
    }
}