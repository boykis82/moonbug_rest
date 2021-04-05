package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.MenuSize;

@Getter
@Setter
@NoArgsConstructor
public class MenuSizePolicyResponseDto {
    private int id;
    private MenuSize menuSize;
    private int price;
    private double calories;

    @Builder
    public MenuSizePolicyResponseDto(
            int id,
            MenuSize menuSize,
            int price,
            double calories) {
        this.id = id;
        this.menuSize = menuSize;
        this.price    = price;
        this.calories = calories;
    }

}
