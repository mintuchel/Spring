package mvc2.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

// WAS 에서 error 를 확인하여
// error-page html 을 request 하는 메시지를 내부로 다시 보냈을 경우
// 여기서 처리된다
@Slf4j
//@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 404");
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage505(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 500");
        return "error-page/500";
    }

    // produces JSON 이면 JSON 을 반환해주는 놈임
    // JSON 들어오면 얘가 우선순위를 갖게 됨
    @RequestMapping(value="/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage500API controller is called");

        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception)request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer)request.getAttribute(ERROR_STATUS_CODE);

        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request){

    }
}
