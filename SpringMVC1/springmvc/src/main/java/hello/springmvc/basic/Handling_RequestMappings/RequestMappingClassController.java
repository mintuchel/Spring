package hello.springmvc.basic.Handling_RequestMappings;

import org.springframework.web.bind.annotation.*;

@RestController
// class-level 에 @RequestMapping 이 쓰여있으면 해당 URL 은 모두 이 클래스에서 처리된다는 거임
// mapping/users 로 들어오는 놈들은 모두 이 클래스에서 처리됨
@RequestMapping("/mapping/users")
public class RequestMappingClassController {

    /**
     * 회원 목록 조회 : GET
     * 회원 등록 : POST
     * 회원 조회 : GET
     * 회원 수정 : PATCH
     * 회원 삭제 : DELETE
     */
    public String user(){
        return "get users";
    }

    public String addUser(){
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId){
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userId="+ userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete userId=" + userId;
    }
}
