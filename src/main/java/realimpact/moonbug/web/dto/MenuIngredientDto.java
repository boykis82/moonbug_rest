package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.MenuSizePolicy;


@Getter
@Setter
@NoArgsConstructor
public class MenuIngredientDto {
    private String ingredientName;
    private String amountWithUnit;
    private MenuSizePolicyDto menuSizePolicy;

    @Builder
    public MenuIngredientDto(String ingredientName, String amountWithUnit, MenuSizePolicyDto menuSizePolicy) {
        this.ingredientName = ingredientName;
        this.amountWithUnit = amountWithUnit;
        this.menuSizePolicy = menuSizePolicy;
    }
}
