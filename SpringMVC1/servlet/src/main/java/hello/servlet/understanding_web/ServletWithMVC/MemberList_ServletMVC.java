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
import java.util.List;

// MVC 패턴이므로 이 서블릿 객체는 Controller 역할이다
// View 는 다른 놈이 처리해준다
@WebServlet(name="mvcMemberListServlet", urlPatterns="/servlet-mvc/members")
public class MemberList_ServletMVC extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        request.setAttribute("members",members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }
}
