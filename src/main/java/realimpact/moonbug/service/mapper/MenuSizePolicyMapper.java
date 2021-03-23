package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuSizePolicyDto;

@Mapper(componentModel = "spring", uses = MenuIngredientMapper.class)
public interface MenuSizePolicyMapper {
    MenuSizePolicyDto entityToDto(MenuSizePolicy entity);

    @ToEntity
    MenuSizePolicy dtoToEntity(MenuSizePolicyDto api);
}