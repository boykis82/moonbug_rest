package realimpact.moonbug.service.mapper;

import org.mapstruct.*;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuSizePolicyDto;

@Mapper(componentModel = "spring", uses = {MenuIngredientMapper.class} )
public abstract class MenuSizePolicyMapper {
    @Mappings({
        @Mapping(target = "menu", ignore = true)
    })
    public abstract MenuSizePolicyDto entityToDto(MenuSizePolicy entity);

    @ToEntity
    public abstract MenuSizePolicy dtoToEntity(MenuSizePolicyDto api);

    @AfterMapping
    public void setMenuSizePolicy(@MappingTarget MenuSizePolicy entity) {
        for (MenuIngredient mi : entity.getMenuIngredients() )
            mi.setMenuSizePolicy(entity);
    }
}