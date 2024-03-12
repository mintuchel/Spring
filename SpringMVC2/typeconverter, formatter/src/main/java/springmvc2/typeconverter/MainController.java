package springmvc2.typeconverter;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmvc2.typeconverter.type.IpPort;

import javax.servlet.http.HttpServletRequest;

// ipPort test 할때는 그냥 Controller
// 아니면 RestController 로 해야함
//@RestController
@Controller
public class MainController {

    // request parameter 들은 모두 문자로 처리됨
    @GetMapping("/ex1")
    public String converter_ex1(HttpServletRequest request){
        String data = request.getParameter("data");
        Integer value = Integer.valueOf(data);
        System.out.println("value =" + value);
        return "converter_ex1 called";
    }

    // @RequestParam 할때 default conversion service 가 동작해서
    // String 타입의 request parameter 를 Integer 로
    // 잘 변환시켜주고 있던 것이다!
    @GetMapping("/ex2")
    public String converter_ex2(@RequestParam Integer data){
        System.out.println("data =" + data);
        return "converter_ex2 called";
    }

    // 객체 타입도 잘 변환시켜준다
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort){
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("upPort PORT = " + ipPort.getPort());
        return "ok";
    }

    // url 이 /converter-view 이면
    // Model 에 객체를 담아서 
    // thymeleaf 가 적용된 html 로 넘겨줘보기
    @GetMapping("/converter-view")
    public String converterView(Model model){
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        return "converter-view";
    }

    // /converter/edit url 이 들어왔을때 보여줄 html 만 전송해주면 됨
    // 이때 form 에 적용되는 converter service 예를 보여주기 위해
    // Model 에 객체를 담아서 전송
    @GetMapping("/converter/edit")
    public String converterForm(Model model){
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Form form = new Form(ipPort);

        model.addAttribute(form);
        return "converter-form";
    }

    // /converter/edit url 로 POST 요청이 왔을때
    // 즉 submit 눌렸을때는 폼에서 client 가 입력한 data 를 가져와야함
    // 그래서 Form 객체로 request parameter 를 받는거임
    // 이때도 converter 호출됨
    @PostMapping("/converter/edit")
    public String converterEdit(@ModelAttribute Form form, Model model){
        IpPort ipPort = form.getIpPort();
        model.addAttribute("ipPort", ipPort);
        return "converter-view";
    }

    @Data
    static class Form{
        private IpPort ipPort;

        public Form(IpPort ipPort){
            this.ipPort = ipPort;
        }
    }
}