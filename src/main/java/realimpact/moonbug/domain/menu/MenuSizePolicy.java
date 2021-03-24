package realimpact.moonbug.domain.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "menu_id") // MenuSizePolicy에 FK로 menu_id 생김
    @JsonIgnore
    private Menu menu;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuSizePolicy")
    private List<MenuIngredient> menuIngredients = new ArrayList<MenuIngredient>();

    @Builder
    public MenuSizePolicy(MenuSize menuSize, int price, double calories, Menu menu) {
        this.menuSize = menuSize;
        this.price    = price;
        this.calories = calories;
        this.menu = menu;
    }

    public void addMenuIngredient(MenuIngredient menuIngredient) {
        menuIngredients.add(menuIngredient);
    }

}
