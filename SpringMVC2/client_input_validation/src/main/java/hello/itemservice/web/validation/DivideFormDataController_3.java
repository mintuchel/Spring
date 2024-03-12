package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemEditForm;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemSaveForm;
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
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class DivideFormDataController_3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "htmlform/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "htmlform/v4/addForm";
    }

    // 상품등록 시에는 다른 객체를 사용해서 받기
    @PostMapping("/add")
    public String addItem2(@Validated @ModelAttribute("item") ItemSaveForm additem, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // GlobalError 잡기
        // GlobalError 는 그냥 직접 BindingResult 에 넣어주는게 더 편함
        if(additem.getPrice()!=null && additem.getQuantity()!=null){
            int resultPrice = additem.getPrice() * additem.getQuantity();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        
        // FieldError 가 있으면
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "htmlform/v4/addForm";
        }

        // 검증 통과된 additem 객체를 item 객체에 복사
        Item item = new Item();
        item.setItemName(additem.getItemName());
        item.setPrice(additem.getPrice());
        item.setQuantity(additem.getQuantity());

        // 그 전과 동일한 저장과정
        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/htmlform/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "htmlform/v4/editForm";
    }

    // 수정 시에는 ItemEditForm 객체로 데이터를 받는다
    // 검증 진행 후 Item 객체로 복사하여 저장
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemEditForm edititem, BindingResult bindingResult) {

        // GlobalError 가 있으면
        if (edititem.getPrice() != null && edititem.getQuantity() != null) {
            int resultPrice = edititem.getPrice() * edititem.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // FieldError 가 있으면
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "htmlform/v4/editForm";
        }

        // 검증통과되었으면 Item 객체에 복사
        Item item = new Item();
        item.setItemName(edititem.getItemName());
        item.setPrice(edititem.getPrice());
        item.setQuantity(edititem.getQuantity());

        itemRepository.update(itemId, item);

        return "redirect:/htmlform/v4/items/{itemId}";
    }
}