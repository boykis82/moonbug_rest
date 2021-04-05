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
public class MenuListResponseDto {
    private int id;
    private String name;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    private MenuCategory menuCategory;

    @Builder
    public MenuListResponseDto(int id,
                               String name,
                               String content,
                               LocalDate startDate,
                               LocalDate expireDate,
                               MenuCategory menuCategory) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.menuCategory = menuCategory;
    }
}
