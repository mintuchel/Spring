package hello.servlet.understanding_web.ServletOnly;

import hello.servlet.Classes.Member;
import hello.servlet.Classes.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "memberFormServlet", urlPatterns = "/servlet/members/save")
public class MemberSave_ServletOnly extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // request 객체로 넘어온 쿼리 값들을 getParameter 로 꺼내주기
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        // Member 객체 생성 후 저장해주기
        Member member = new Member(username, age);
        memberRepository.save(member);

        // response 객체 설정
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // HTML 코드 클래스 내 직접 작성
        PrintWriter w = response.getWriter();
        w.write("<html>\n +" +
                "<head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                "   <li>id=" + member.getId() + "</li>\n" +
                "   <li>username=" + member.getUsername() + "</li>\n" +
                "   <li>age=" + member.getAge() + "</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");
    }
}
