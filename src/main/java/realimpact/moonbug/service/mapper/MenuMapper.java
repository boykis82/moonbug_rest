package realimpact.moonbug.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuDto;

@Mapper(componentModel = "spring", uses = MenuSizePolicyMapper.class)
public abstract class MenuMapper {
    public abstract MenuDto entityToDto(Menu entity);

    @ToEntity
    public abstract Menu dtoToEntity(MenuDto api);

    @AfterMapping
    public void setMenu(@MappingTarget Menu entity) {
        for (MenuSizePolicy msp : entity.getMenuSizePolicies() )
            msp.setMenu(entity);
    }
}
