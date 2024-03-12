package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemEditForm {
    @NotNull
    private Long id;
    
    @NotBlank
    private String itemName;
    
    @NotNull
    @Range(min=1000, max=1000000)
    private Integer price;
    
    // 수정할때 수량에 대한 제한은 없다
    private Integer quantity;
}