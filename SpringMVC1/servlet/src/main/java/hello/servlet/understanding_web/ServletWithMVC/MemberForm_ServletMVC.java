package hello.servlet.understanding_web.ServletWithMVC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet_Only 와는 다르게 View 와 Servlet 클래스와 분리시켜줌
// 항상 Controller 를 거치고 View 와 Service 로 간다!

// 참고로 SpringMVC 에서는 @Controller 로 "@WebServlet annotation 과 클래스의 extends HttpServlet" 을 퉁 칠 수 있다!

@WebServlet(name="mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MemberForm_ServletMVC extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // View 의 jsp 경로지정
        String viewPath="/WEB-INF/views/new-form.jsp";

        // Controller 에서 View 로 이동할때 사용하는 코드
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);

        // servlet 에서 JSP 를 찾아 넘어가서 호출
        // 다른 서블릿이나 JSP 로 이동할 수 있는 기능이다.
        // 서버 내부에서 다시 호출이 발생한다. 클라이언트에서 다시 요청을 보내는게 아니다.
        dispatcher.forward(request,response);
    }
}
