package realimpact.moonbug.service.mapper;

import org.mapstruct.*;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuSizePolicyCreateRequestDto;

@Mapper(componentModel = "spring", uses = {MenuIngredientCreateMapper.class} )
public abstract class MenuSizePolicyCreateMapper {

    @ToEntity
    public abstract MenuSizePolicy dtoToEntity(MenuSizePolicyCreateRequestDto api);

    @AfterMapping
    public void setMenuSizePolicy(@MappingTarget MenuSizePolicy entity) {
        for (MenuIngredient mi : entity.getMenuIngredients() ) {
            //entity.addMenuIngredient(mi);
            mi.setMenuSizePolicy(entity);
        }
    }
}
