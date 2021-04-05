package realimpact.moonbug.service.mapper;

import org.mapstruct.Mapper;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.web.dto.MenuSizePolicyResponseDto;

@Mapper(componentModel = "spring")
public interface MenuSizePolicyResponseMapper {
    MenuSizePolicyResponseDto entityToDto(MenuSizePolicy entity);
}
