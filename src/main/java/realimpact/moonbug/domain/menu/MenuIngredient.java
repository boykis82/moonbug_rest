package realimpact.moonbug.domain.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import realimpact.moonbug.domain.BaseEntity;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "menu_ingredient")
public class MenuIngredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, nullable = false)
    private String ingredientName;

    @Column(length = 10, nullable = false)
    private String amountWithUnit;

    @Builder
    public MenuIngredient(String ingredientName, String amountWithUnit) {
        this.ingredientName = ingredientName;
        this.amountWithUnit = amountWithUnit;
    }
}
