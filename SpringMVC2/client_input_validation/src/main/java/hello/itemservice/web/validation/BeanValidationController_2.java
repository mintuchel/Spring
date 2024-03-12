package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class BeanValidationController_2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "htmlform/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "htmlform/v3/addForm";
    }

    // 스프링 자동 검증기인 BeanValidator 를 사용
    // @Validated 이므로 해당 객체는 검증기가 검사를 진행함
    // error 가 있으면 BindingResult 에 자동으로 저장함
    @PostMapping("/add")
    public String addItem2(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // GlobalError 잡기
        // GlobalError 는 그냥 직접 BindingResult 에 넣어주는게 더 편함
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{resultPrice}, null);
            }
        }
        
        // FieldError 가 있으면
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "htmlform/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/htmlform/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v3/editForm";
    }

    // 만약 수정을 요청하면
    // 내부적으로 수정된 Item 값을 업데이트 하고
    // 해당 itemId 가 업데이트 된 화면을 볼 수 있게
    // redirect 를 시켜 GET 방식으로 itemId 를 볼 수 있게 한다
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) {

        // GlobalError 가 있으면
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            }
        }

        // FieldError 가 있으면
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "htmlform/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/htmlform/v3/items/{itemId}";
    }
}