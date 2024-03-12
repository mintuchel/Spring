package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5.adapter;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.ControllerV3;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 만약 들어온 handler 가 V3 용이면
// V5 에서는 이 객체를 통해 V3 버전이라는 것을 확인하고 V3 의 paramMap 을 받는다
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3)handler;

        Map<String, String> paramMap = createParmMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    // V3 에서 사용한거 그대로 따옴
    private Map<String,String> createParmMap(HttpServletRequest request){
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
