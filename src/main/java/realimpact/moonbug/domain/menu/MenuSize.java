package realimpact.moonbug.domain.menu;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import realimpact.moonbug.util.enumutil.EnumModel;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MenuSize implements EnumModel {
    SHORT("Short"),
    TALL("Tall"),
    GRANDE("Grande"),
    VENTI("Venti");

    private final String value;

    private MenuSize(String value) {
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
    public static MenuSize forValues(@JsonProperty("key") String key,
                                         @JsonProperty("value") String value) {
        for (MenuSize menuSize : MenuSize.values()) {
            if (menuSize.getKey().equals(key) && menuSize.getValue().equals(value)) {
                return menuSize;
            }
        }
        return null;
    }

}
