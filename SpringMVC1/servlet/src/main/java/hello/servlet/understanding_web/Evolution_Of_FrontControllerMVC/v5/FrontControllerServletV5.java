package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.MyView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberFormControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberListControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller.MemberSaveControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v4.controller.MemberFormControllerV4;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v4.controller.MemberListControllerV4;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v4.controller.MemberSaveControllerV4;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 존재하는 모든 컨트롤러가 다 들어가야함 그래서 Object 가 value 로 들어감
    // 그래야 Controller 객체들을 받을 수 있기 때문임
    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    // V1 V2 V3 V4 중 어떤 handler 인지 확인하고 찾아써야함
    // 그래서 어떤 handler 인지 확인하는 걸 이 list 를 통해서 함
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5(){
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        // V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        // V4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    // 어떤 버전인지 체크하고 해당 버전 front controller 반환
    private MyHandlerAdapter getHandlerAdapter(Object handler){
        for(MyHandlerAdapter adapter : handlerAdapters){
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다");
    }
    
    // 버전 확인 된 상태에서 request 에 따른 해당 버전의 controller 를 호출해주기
    private Object getHandler(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(request);
    }

    private MyView viewResolver(String viewName) {
        return new MyView(viewName);
    }
}
