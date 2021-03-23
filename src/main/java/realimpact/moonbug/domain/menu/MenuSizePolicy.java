package realimpact.moonbug.domain.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "menu_size_policy")
public class MenuSizePolicy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MenuSize menuSize;

    @Column(nullable = false)
    private int price;

    @Column
    private double calories;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_size_policy_id")
    private List<MenuIngredient> menuIngredients = new ArrayList<MenuIngredient>();

    @Builder
    public MenuSizePolicy(MenuSize menuSize, int price, double calories) {
        this.menuSize = menuSize;
        this.price    = price;
        this.calories = calories;
    }

    public void addMenuIngredient(MenuIngredient menuIngredient) {
        menuIngredients.add(menuIngredient);
    }

}
