package hello.servlet.HttpBasic_Practice.HttpRequest_Practice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// HTTP URL 로 온 query parameter 들을 추출해보자

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamPractice extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request 객체의 전체 parameter 출력
        System.out.println("1. [전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("2. [단일 파라미터 조회]");
        // 단일 파라미터 조회
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();
    }
}
