package hello.itemservice.domain.item;

import lombok.Data;

// 이제는 addForm 일때 받는 item 객체와
// editForm 일때 받는 item 객체가 다르다

// 각 다른 종류의 객체로 검증을 다 마친 후
// 이 Item 객체에다 복사할 것이므로
// 검증이 된 데이터들이 들어오는 것이니 여기서는 검증이 필요가 없다!
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){

    }

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
