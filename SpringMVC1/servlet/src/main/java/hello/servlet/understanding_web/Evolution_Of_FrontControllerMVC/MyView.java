package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// V2에서 각 specific controller 가 반환하는 객체이다.
// view 를 생성하는데 필요한 정보를 담는 객체이다.
public class MyView {

    // jsp 파일의 경로가 들어감
    // 여기서는 "/WEB-INF/views/new-form.jsp"
    private String viewPath;

    // 생성자
    public MyView(String viewPath) { this.viewPath = viewPath; }

    // V2의 dispatcher.forward 작업을 이쪽으로 빼냄
    // V2는 HttpServletRequest 를 사용하므로 이 render 함수는 인자가 HttpServlet 임.
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    // V3의 controller 들은 Model 로만 작업하므로 이 render 함수는 인자가 Model 임
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // Model parameter 로 Request 초기화
        modelToRequestAttribute(model,request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
    
    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request){
        model.forEach((key,value) -> request.setAttribute(key,value));
    }
}
