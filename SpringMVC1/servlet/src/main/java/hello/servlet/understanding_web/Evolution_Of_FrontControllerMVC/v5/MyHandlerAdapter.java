package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Adapter 클래스들이 상속할 Interface
public interface MyHandlerAdapter {
    boolean supports(Object handler);
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
