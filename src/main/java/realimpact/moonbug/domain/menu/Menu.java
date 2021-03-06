package realimpact.moonbug.domain.menu;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate expireDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MenuCategory menuCategory;

    // MenuSizePolicy가 연관관계의 주인이다. (FK 소유) mappedBy의 값은 MenuSizePolicy의 menu 속성을 의미함
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<MenuSizePolicy> menuSizePolicies = new ArrayList<MenuSizePolicy>();

    @Builder
    public Menu(String name,
                String content,
                LocalDate startDate,
                LocalDate expireDate,
                MenuCategory menuCategory) {
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.menuCategory = menuCategory;
    }

    public void addMenuSizePolicy(MenuSizePolicy menuSizePolicy) {
        this.menuSizePolicies.add(menuSizePolicy);
        menuSizePolicy.setMenu(this);
    }

    public Optional<MenuSizePolicy> getMenuSizePolicy(MenuSize menuSize) {
        for(MenuSizePolicy msp : menuSizePolicies) {
            if (msp.getMenuSize() == menuSize) {
                return Optional.of(msp);
            }
        }
        return Optional.empty();
    }

    public void expire(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
