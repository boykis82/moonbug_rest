package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.domain.menu.MenuSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MenuSizePolicyDto {
    private MenuSize menuSize;
    private int price;
    private double calories;

    private MenuDto menu;
    private List<MenuIngredientDto> menuIngredients = new ArrayList<MenuIngredientDto>();

    @Builder
    public MenuSizePolicyDto(MenuSize menuSize, int price, double calories, MenuDto menu, List<MenuIngredientDto> menuIngredients) {
        this.menuSize = menuSize;
        this.price    = price;
        this.calories = calories;
        this.menu = menu;
        this.menuIngredients = menuIngredients;
    }
}
