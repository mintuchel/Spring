package hello.servlet.HttpBasic_Practice.HttpRequest_Practice;

// JSON API 형식의 request 를 다루는 거라
// 일반 HTML 과는 성격이 다름
// JSON 으로 보내지는 request 는 Header 부분의 Content-Type 이 json 으로 바뀌어있음
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.HttpBasic_Practice.JSON_data;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class JSON_RequestBodyPractice extends HttpServlet {

    // JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 JSON 변환 라이브러리를 추가해서 사용해야 한다
    // Spring 은 기본적으로 jackson 을 기본적으로 제공해줌(ObjectMapper)
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        // 여기가 json 방식의 핵심이다
        // message body 로 온 값들을 HelloData 객체에 넣어주기
        JSON_data data = objectMapper.readValue(messageBody, JSON_data.class);

        System.out.println("RequestedData's username = " + data.getUsername());
        System.out.println("RequestedData's age = " + data.getAge());
    }
}
