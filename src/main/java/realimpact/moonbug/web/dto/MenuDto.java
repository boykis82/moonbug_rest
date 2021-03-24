package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realimpact.moonbug.domain.menu.MenuCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MenuDto {
    private String name;
    private String content;
    private LocalDate startDate;
    private LocalDate expireDate;
    private MenuCategory menuCategory;
    private List<MenuSizePolicyDto> menuSizePolicies = new ArrayList<MenuSizePolicyDto>();

    @Builder
    public MenuDto(String name,
                              String content,
                              LocalDate startDate,
                              LocalDate expireDate,
                              MenuCategory menuCategory,
                              List<MenuSizePolicyDto> menuSizePolicies) {
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.menuCategory = menuCategory;
        this.menuSizePolicies = menuSizePolicies;
    }

}
