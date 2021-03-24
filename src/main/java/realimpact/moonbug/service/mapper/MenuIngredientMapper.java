package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.web.dto.MenuIngredientDto;

@Mapper(componentModel = "spring")
public interface MenuIngredientMapper {
    @Mappings({
            @Mapping(target = "menuSizePolicy", ignore = true)
    })
    MenuIngredientDto entityToDto(MenuIngredient entity);

    @ToEntity
    MenuIngredient dtoToEntity(MenuIngredientDto api);
}
