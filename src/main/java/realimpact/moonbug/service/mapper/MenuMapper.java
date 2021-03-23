package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.web.dto.MenuDto;

@Mapper(componentModel = "spring", uses = MenuSizePolicyMapper.class)
public interface MenuMapper {
    MenuDto entityToDto(Menu entity);

    @ToEntity
    Menu dtoToEntity(MenuDto api);
}
