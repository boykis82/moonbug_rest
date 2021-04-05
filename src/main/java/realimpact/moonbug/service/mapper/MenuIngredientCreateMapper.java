package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.web.dto.MenuIngredientCreateRequestDto;

@Mapper(componentModel = "spring")
public interface MenuIngredientCreateMapper {

    @ToEntity
    MenuIngredient dtoToEntity(MenuIngredientCreateRequestDto api);
}
