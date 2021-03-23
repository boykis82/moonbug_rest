package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MenuIngredientDto {
    private String ingredientName;
    private String amountWithUnit;

    @Builder
    public MenuIngredientDto(String ingredientName, String amountWithUnit) {
        this.ingredientName = ingredientName;
        this.amountWithUnit = amountWithUnit;
    }
}
