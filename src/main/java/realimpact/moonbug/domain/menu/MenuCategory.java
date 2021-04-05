package realimpact.moonbug.domain.menu;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import realimpact.moonbug.util.enumutil.EnumModel;

import static java.util.stream.Collectors.toMap;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MenuCategory implements EnumModel {
    DRINK("음료"),
    FOOD ("푸드"),
    PROD ("상품");

    private final String value;

    private MenuCategory(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MenuCategory forValues(@JsonProperty("key") String key,
                                     @JsonProperty("value") String value) {
        for (MenuCategory menuCategory : MenuCategory.values()) {
            if (menuCategory.getKey().equals(key) && menuCategory.getValue().equals(value)) {
                return menuCategory;
            }
        }
        return null;
    }
}
