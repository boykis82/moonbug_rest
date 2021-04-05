package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.web.dto.MenuResponseDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = MenuSizePolicyResponseMapper.class)
public interface MenuResponseMapper {
    MenuResponseDto entityToDto(Menu entity);
    List<MenuResponseDto> entityListToDtoList(List<Menu> entity);
}
