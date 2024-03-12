package hello.servlet.understanding_web.ServletOnly;


import hello.servlet.Classes.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Servlet 만 사용하기 때문에
// HTML 파일을 Servlet 클래스 내에서 직접 작성해야한다
// writer 로 화면 내에 표시될 것들을 직접 써야한다

@WebServlet(name="memberFormatServlet", urlPatterns="/servlet/members/new-form")
public class MemberForm_ServletOnly extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // response 객체 설정
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // HTML 직접 작성
        PrintWriter w = response.getWriter();
        w.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "   <meta charset=\"URF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/servlet/members/save\" method=\"post\">\n" +
                "   username: <input type=\"text\" name=\"username\" />\n" +
                "   age:    <input type=\"text\" name=\"age\" />\n" +
                "   <button type=\"submit\">전송</button>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>\n");
    }
}
