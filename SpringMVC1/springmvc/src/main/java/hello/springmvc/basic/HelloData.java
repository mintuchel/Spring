package hello.springmvc.basic;

import lombok.Data;

// @ModelAttribute 을 위해 만든 간단한 클래스이다
// @Data 는 @Getter @Setter @ToString @RequiredArgsConstructor 를 자동으로 만들어준다
@Data
public class HelloData {
    private String username;
    private int age;
}
