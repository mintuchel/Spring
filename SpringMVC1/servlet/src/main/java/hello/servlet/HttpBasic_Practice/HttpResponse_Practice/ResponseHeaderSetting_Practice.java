package hello.servlet.HttpBasic_Practice.HttpResponse_Practice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// response 응답 객체를 설정하는 방식을 배워보자
// header, content, cookie 등을 설정할 수 있다

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderSetting_Practice extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line] 설정
        // 200 정수 적는 것보다 상수 값 사용하는게 나음
        response.setStatus(HttpServletResponse.SC_OK);

        // [response-headers] 설정
        response.setHeader("Content-Type","text/plain");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        content(response);
        cookie(response);
        redirect(response);

        // 단순 텍스트 응답
        PrintWriter writer = response.getWriter();
        writer.println("ok");
    }

    // response.setHeader("Content-Type", "text/plain;charset=utf-8");
    // 이렇게 해도 되는 거를 함수를 만들어서 해도 된다
    private void content(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }

    // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
    // 이렇게 해도 되는 거를 함수를 만들어서 해도 된다
    private void cookie(HttpServletResponse response){
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); // 600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("/basic/hello-form.html");
    }
}
