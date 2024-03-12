package hello.servlet.HttpBasic_Practice.HttpRequest_Practice;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// API 방식에서 데이터를 추출하는 예제이다.

// 내가 직접 message body 에 데이터를 직접 실어서 보내는 방식에서
// 어떻게 데이터를 추출하는지 알아보자
// 우선 가장 단순한 텍스트 메세지를 다루는 법을 알아보자
@WebServlet(name="requestBodyStringServlet", urlPatterns="/request-body-string")
public class API_RequestBodyPractice extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // message body 를 바이트 코드로 변환
        ServletInputStream inputStream = request.getInputStream();
        // 바이트 코드를 string 으로 바꿔주기
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
    }
}
