package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;

import java.util.Map;

// 사실 각 specific controller 에 HttpServlet 인자들을 전달할 필요가 없다
// 그냥 Client 의 request 에서 요구하는 requested parameter 가 무엇인지만 전달하면 된다
// 따라서 parameter 정보만 HashMap 데이터로 전달해보자
// -> 이제 Servlet 기술에 종속적이지 않은 Controller 를 만드는거임.

public interface ControllerV3 {
    // 위에서 설명했다시피 인자가 HttpServlet 객체들이 아님.
    ModelView process(Map<String, String> paramMap);
}