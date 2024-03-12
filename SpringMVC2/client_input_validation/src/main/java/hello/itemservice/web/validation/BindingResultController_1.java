package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class BindingResultController_1 {

    private final ItemRepository itemRepository;

    // items.html 에는 Model 에 전체 items 만 넣어서 보내주면 됨
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "htmlform/v2/items";
    }

    // 특정 itemId 에 대한 정보를 요구하면
    // item.html 에는 해당 특정 item 에 대한 정보만 Model 에 넣어서 보내주면 됨
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v2/item";
    }

    // 상품등록 폼을 요청하면
    // 빈 item 을 생성해서 Model 에 넣어주고 이걸 addForm.html 로 보내면 됨
    // 빈 Item 객체이므로 모두 빈칸으로 뜨는거임
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "htmlform/v2/addForm";
    }

    // 상품등록을 요청하면
    // @ModelAttribute 로 client 가 보낸 상품객체를 받아주고
    // 이를 내부적으로 ItemRepository 에 저장한 다음
    // 에러가 났으면 Model 에 에러를 넣어 다시 addForm 으로 보내주고
    // 아니면 등록된 item 을 보여주기 위해 item.html 로 redirect 해줌
    // 즉 client 가 강제로 GET 방식으로 item.html 을 요청하게 만든다
    @PostMapping("/add")
    public String addItem1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        
        // rejectValue 로 FieldError 넣기
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName","required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity","max", new Object[]{10000}, null);
        }
        
        // reject 로 GlobalError 넣기
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMine", new Object[]{10000, resultPrice}, null);
            }
        }

        // BindingResult 에 error 가 담겨있다면
        // addForm html 을 client 에게 다시 보여주기
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "htmlform/v2/addForm";
        }

        // error 가 없다면
        // 내부적으로 itemRepository 에 저장
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/htmlform/v2/items/{itemId}";
    }

    // 만약 client 가 버튼을 눌러 수정폼을 요청하면
    // client 가 수정을 원하는 아이템 id 를 받고
    // 해당 item 정보를 Model 객체에 담아
    // editForm.html 이 사용할 수 있게 보내면 됨
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v2/editForm";
    }

    // 만약 수정을 요청하면
    // 내부적으로 수정된 Item 값을 업데이트 하고
    // 해당 itemId 가 업데이트 된 화면을 볼 수 있게
    // redirect 를 시켜 GET 방식으로 itemId 를 볼 수 있게 한다
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/htmlform/v2/items/{itemId}";
    }
}
