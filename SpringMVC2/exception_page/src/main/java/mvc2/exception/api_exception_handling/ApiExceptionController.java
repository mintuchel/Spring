package mvc2.exception.api_exception_handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// API 로 왔을때 예외처리까지 해주는 controller (JSON 처리)
// 예외처리한 후, 객체를 반환하면 알아서 JSON 객체로 변환하여 client 에게 JSON 으로 반환해줌
// @ExceptionHandler 있는 controller 로 예외잡기 가능
@RestController
@Slf4j
public class ApiExceptionController {

    // IllegalArgumentException 만 잡아줌
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e){
        log.error("IllegalArgumentException handled");
        return new ErrorResult("BAD", e.getMessage());
    }

    // 이렇게 잡을 예외 종류 생략 가능
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExceptionHandler(UserException e){
        log.error("userException handled");
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // Exception 상속하는 것들은 모두 잡아줌
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception e){
        log.error("Exception handled");
        return new ErrorResult("EX", "내부오류");
    }

    // client request 처리
    // 예외가 발생했으면 위 controller 들로 처리
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) throws UserException {
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력값");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello "+id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
