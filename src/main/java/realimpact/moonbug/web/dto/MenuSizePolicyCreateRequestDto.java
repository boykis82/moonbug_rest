package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.MenuSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuSizePolicyCreateRequestDto {
    private MenuSize menuSize;
    private int price;
    private double calories;

    private List<MenuIngredientCreateRequestDto> menuIngredients = new ArrayList<MenuIngredientCreateRequestDto>();

    @Builder
    public MenuSizePolicyCreateRequestDto(MenuSize menuSize,
                             int price,
                             double calories) {
        this.menuSize = menuSize;
        this.price    = price;
        this.calories = calories;
    }

    public void addMenuIngredientCreateRequestDto(MenuIngredientCreateRequestDto menuIngredientCreateRequestDto) {
        if( menuIngredients == null )
            menuIngredients = new ArrayList<MenuIngredientCreateRequestDto>();
        this.menuIngredients.add(menuIngredientCreateRequestDto);
    }
}
