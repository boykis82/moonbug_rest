package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import realimpact.moonbug.domain.menu.MenuCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuCreateRequestDto {
    private String name;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    private MenuCategory menuCategory;

    private List<MenuSizePolicyCreateRequestDto> menuSizePolicies
            = new ArrayList<MenuSizePolicyCreateRequestDto>();

    @Builder
    public MenuCreateRequestDto(
                    String name,
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

    public void addMenuSizePolicy(MenuSizePolicyCreateRequestDto menuSizePolicyCreateRequestDto) {
        if (this.menuSizePolicies == null)
            menuSizePolicies = new ArrayList<MenuSizePolicyCreateRequestDto>();
        this.menuSizePolicies.add(menuSizePolicyCreateRequestDto);
    }

}
