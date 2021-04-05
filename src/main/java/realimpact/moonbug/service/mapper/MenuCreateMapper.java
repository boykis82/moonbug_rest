package realimpact.moonbug.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuCreateRequestDto;

@Mapper(componentModel = "spring", uses = MenuSizePolicyCreateMapper.class)
public abstract class MenuCreateMapper {

    @ToEntity
    public abstract Menu dtoToEntity(MenuCreateRequestDto api);

    @AfterMapping
    //-- createdto -> entity 매핑 후 호출됨. 각 size policy
    public void setMenu(@MappingTarget Menu entity) {
        for (MenuSizePolicy msp : entity.getMenuSizePolicies() ) {
            //entity.addMenuSizePolicy(msp);
            msp.setMenu(entity);
        }
    }
}
