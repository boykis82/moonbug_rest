package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.web.dto.MenuIngredientDto;

@Mapper(componentModel = "spring")
public interface MenuIngredientMapper {
    MenuIngredientDto entityToDto(MenuIngredient entity);

    @ToEntity
    MenuIngredient dtoToEntity(MenuIngredientDto api);
}
