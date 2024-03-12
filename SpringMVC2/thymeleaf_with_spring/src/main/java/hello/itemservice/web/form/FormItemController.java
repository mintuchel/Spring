package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
// 자동 Bean 의존성 주입. 여기서는 itemRepository 에 대한 의존성 주입 생성자를 알아서 만들어줌.
// private final 에 대한 자동 의존성 주입
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    // ModelAttribute 를 이렇게 빼놓으면
    // 해당 함수에서 return 된 값이 저절로 Model 에 담김
    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String,String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    // 항상 Model 에 ENUM 을 모두 배열로 담아놓기
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values();
    }

    // 항상 Model 에 deliveryCode 담아놓기
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    // "/form/items" 로 GET request 가 들어오면
    // Model 에 items 를 넣어 DispatcherServlet 한테 전달한다.
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "htmlform/items" ;
    }

    // 하나의 item 만 보이는 url 이므로 Model 에 해당 item 하나의 정보만 담아 보내주면 된다
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/item";
    }

    // addForm.html 로드할때 빈 객체를 Model 에 넣어 반환
    // 이 Model 은 addForm.html 에서 thymeleaf 로 렌더링 될때 사용된다
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "htmlform/addForm";
    }

    // 여긴 왜 Model 이 필요가 없을까
    // Model 로 할 작업이 없기 때문. 근데 Model 은 parameter 로 전달 안해도 자동으로 전달되긴 한다.
    // 내부적으로 새로운 item 을 저장해주고
    // redirect 만 시키면 되기 때문
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        Item savedItem = itemRepository.save(item);

        // add Attributes for redirection
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        // POST add 는 새 view 를 보내줄 필요가 없다
        // 어차피 redirect 시켜줄 것이기 때문이다.
        // redirect 를 통해 client 가 해당 url 로 새 요청을 보내도록 만들자
        // 이러면 redirect 하는 url 에 맞는 view 를 제공받을 수 있게 되어있다.
        return "redirect:/form/items/{itemId}";
    }

    // 지금 Controller 에서 View 로 Item 을 Model 에 넣어 전달해주고 있음
    // 따라서 editForm 에서 thymeleaf 로 item 이라는 Model 내 객체를 참조하는 코드가 있어야함!
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }
}