package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v1;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v1.controller.MemberFormControllerV1;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v1.controller.MemberListControllerV1;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// FrontController 이므로 최초이자 마지막인 서블릿
// 즉 유일한 servlet 이다.
// 해당 servlet 은 /front-controller/v1를 포함한 모든 하위요청을 받을 수 있다!
@WebServlet(name="frontControllerServletV1", urlPatterns="/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet{

    // Hashmap 에 특정 <URL, Controller> 형식으로 저장해준다
    private Map<String,ControllerV1> controllerMap = new HashMap<>();

    // 생성자로 Hashmap 에 값들을 넣어준다.
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request 로 받은 객체의 URI 가 HashMap 에 있는 key 값으로 있는 String 중 하나랑 매칭이 될거임
        // 왜냐하면 우리는 모든 URL 경우의 수를 HashMap 에 저장해놓았기 때문!
        // 그래서 URI 로 조회해서 실제 호출할 controller 를 찾는다.
        String requestURI = request.getRequestURI();

        // 해당 URI 의 key 값으로 controller 객체 받기
        ControllerV1 controller = controllerMap.get(requestURI);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 정의한 process 함수로 request response 객체 전달
        controller.process(request,response);
    }
}
