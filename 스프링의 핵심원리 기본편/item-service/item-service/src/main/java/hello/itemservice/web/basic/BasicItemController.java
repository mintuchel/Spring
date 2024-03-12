package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    // "/basic/items" 기본 화면
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 특정 item 화면
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    // POST-HTML FORM
    // @RequestMapping 쓰지말고 @ModelAttribute 로 하자
    // refresh 하면 POST 가 또 보내지는 것을 방지하기 위해 redirect 를 쓰자
    // 여기서 더 나아가 redirectAttribute 를 쓰면 client 친화적인 ui 를 추가할 수 있다

    // 얘로 하면 client 의 URL 이 안바뀜
    // /add 그대로임. 그냥 html 만 변경된거고
    // 즉 이 메서드로 하면 server 쪽에서 그냥 바뀐 view 만 return 하는 거지
    // client browser 의 URL 까지 바꾸지는 않음
    // 그래서 client 는 계속 /add URL 에 남아있음
    // 이걸 view 가 바꼈다고 URL 도 동시에 같이 바뀐다고 생각하면 안됨
    /*
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, Model model){
        Item savedItem = itemRepository.save(item);
        return "basic/item";
    }
    */

    // 얘로 해야지 html 도 변경되고 client 의 URL 도 변경이 됨
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item savedItem =itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        //return "redirect:/basic/items/" + savedItem.getId();
        return "redirect:/basic/items/{itemId}";
    }

    // 상품수정
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        // edit 했을 시 URL 을 강제로 바꾸고 싶음 -> redirect 사용
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("A",1000,10));
        itemRepository.save(new Item("B",2000,10));
    }
}
