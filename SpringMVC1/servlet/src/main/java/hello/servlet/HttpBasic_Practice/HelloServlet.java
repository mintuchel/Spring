package hello.servlet.HttpBasic_Practice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 이 servlet 은 localhost:8080/hello 가 입력되면 실행됨!
@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // 이 servlet 이 호출되면 이 service 함수가 호출됨
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // 여기서부터 request 를 파싱해주고
        // response 를 다룰 수 있게 함수들을 지원함

        // servlet 은 쿼리파라미터를 매우 편리하게 읽을 수 있도록 지원함
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);
    }
}
