package realimpact.moonbug.domain.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.BaseEntity;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu_ingredient")
public class MenuIngredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, nullable = false)
    private String ingredientName;

    @Column(length = 10, nullable = false)
    private String amountWithUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_size_policy_id")
    @JsonIgnore
    private MenuSizePolicy menuSizePolicy;

    @Builder
    public MenuIngredient(String ingredientName,
                          String amountWithUnit,
                          MenuSizePolicy menuSizePolicy) {
        this.ingredientName = ingredientName;
        this.amountWithUnit = amountWithUnit;
        this.menuSizePolicy = menuSizePolicy;
    }

}
