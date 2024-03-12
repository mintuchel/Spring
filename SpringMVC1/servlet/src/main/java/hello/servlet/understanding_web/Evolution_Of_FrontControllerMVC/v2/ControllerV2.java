package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v2;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    // V1과 동일하게 process 함수와 Interface 를 도입한다.
    // 하지만 process 의 return type 이 다르다.
    // 왜냐하면 V2는 view 처리를 각 controller 에서 하는게 아닌 별도처리를 하기 때문이다.
    // return type 이 MyView 이다.

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}