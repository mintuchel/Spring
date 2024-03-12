package hello.servlet.HttpBasic_Practice.HttpResponse_Practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.HttpBasic_Practice.JSON_data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="responseJsonServlet", urlPatterns="/response-json")
public class ResponseByJson_Practice extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // response 할 JSON 객체 생성
        JSON_data data = new JSON_data();
        data.setUsername("kim");
        data.setAge(20);

        // 이걸 JSON 형식으로 바꿔야함
        // {"username":"kim", "age":20}
        String result = objectMapper.writeValueAsString(data);

        response.getWriter().write(result);
    }
}
