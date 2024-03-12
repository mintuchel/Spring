package hello.servlet.understanding_web.ServletWithMVC;

import hello.servlet.Classes.Member;
import hello.servlet.Classes.MemberRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="mvcMemberSaveServlet", urlPatterns="/servlet/members/new-form")
public class MemberSave_ServletMVC extends HttpServlet {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username,age);
        memberRepository.save(member);

        // 여기서는 Model 이 HttpRequest 객체의 내부저장소임
        // HttpRequest 객체의 내부저장소에 데이터 저장
        request.setAttribute("member",member);

        // View 를 사용하기 위해 경로 지정!
        String viewPath = "/WEB-INF/vies/save-result.jsp";
        // dispatcher 에 view 경로 설정해주고
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        // 데이터랑 같이 던져주기
        dispatcher.forward(request,response);
    }
}
