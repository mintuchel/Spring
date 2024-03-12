package hello.servlet.HttpBasic_Practice.HttpResponse_Practice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//

@WebServlet(name="responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseByHTML_Practice extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type 세팅해주기
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // writer 를 통해서 html 노가다 작성해주기
        // 지금은 연습하느라 이렇게 하는거고 실제로는 이렇게 html 을 써줄 일은 없다
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("    <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
