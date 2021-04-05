package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuIngredientsResponseDto {
    private int id;
    private String ingredientName;
    private String amountWithUnit;

    @Builder
    public MenuIngredientsResponseDto(String ingredientName, String amountWithUnit) {
        this.ingredientName = ingredientName;
        this.amountWithUnit = amountWithUnit;
    }
}
