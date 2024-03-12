package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.MyView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberFormControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberListControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns="/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap();

    public FrontControllerServletV3(){
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 각 controller 에 전달할 parameter 정보를 저장할 HashMap 생성
        Map<String, String> paramMap = createParmMap(request);

        // paramMap 에 정보 저장
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        // specific 한 controller 에 paramMap 넘겨주고
        // ModelView 객체를 받기
        ModelView mv = controller.process(paramMap);

        // 이거는 논리이름만 출력됨. 물리이름(전체이름)을 찾기 위해
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    private Map<String,String> createParmMap(HttpServletRequest request){
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
