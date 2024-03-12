package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 {

    // servlet 의 service 를 본떠서 만든 process 함수를 갖는 Interface 를 준비한다.
    // 이제 각 controller 들은 이 인터페이스를 구현하면 된다.
    // front controller 는 이 인터페이스를 호출해서 각 controller 에서의 구현과 관계없이
    // process 함수만 호출하여 logic 의 일관성을 가져갈 수 있다.

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
