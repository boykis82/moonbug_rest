package realimpact.moonbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<MenuSizePolicyDto> menuSizePolicies = new ArrayList<MenuSizePolicyDto>();

    @Builder
    public MenuDto(String name,
                              String content,
                              LocalDate startDate,
                              List<MenuSizePolicyDto> menuSizePolicies) {
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.menuSizePolicies = menuSizePolicies;
    }

}
