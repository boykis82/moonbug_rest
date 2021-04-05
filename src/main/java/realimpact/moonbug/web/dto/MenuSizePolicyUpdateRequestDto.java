package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.domain.menu.MenuSize;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class MenuSizePolicyUpdateRequestDto {
    @Column(nullable = false)
    private int price;

    @Column
    private double calories;

    @Builder
    public MenuSizePolicyUpdateRequestDto(int price, double calories) {
        this.price    = price;
        this.calories = calories;
    }
}
