package realimpact.moonbug.domain.menu;

import lombok.Getter;

@Getter
public enum MenuCategory {
    NONE("없음"),
    DRINK("음료"),
    FOOD("푸드"),
    PROD("상품");

    private final String name;

    private MenuCategory(String name) {
        this.name = name;
    }
}
