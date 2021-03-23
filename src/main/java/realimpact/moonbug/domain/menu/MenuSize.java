package realimpact.moonbug.domain.menu;

import lombok.Getter;

@Getter
public enum MenuSize {
    SHORT("Short"), TALL("Tall"), GRANDE("Grande"), VENTI("Venti");

    private final String name;

    private MenuSize(String name) {
        this.name = name;
    }
}
