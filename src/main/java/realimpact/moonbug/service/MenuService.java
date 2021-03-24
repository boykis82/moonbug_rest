package realimpact.moonbug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.domain.menu.MenuCategory;
import realimpact.moonbug.domain.menu.MenuRepository;
import realimpact.moonbug.exception.InvalidInputException;
import realimpact.moonbug.exception.NotFoundException;
import realimpact.moonbug.service.mapper.MenuMapper;
import realimpact.moonbug.web.dto.MenuDto;

import java.util.List;

@Service
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper mapper;

    @Autowired
    public MenuService(MenuRepository menuRepository,
                       MenuMapper mapper) {
        this.mapper = mapper;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public int createMenu(MenuDto menuDto) {
        return menuRepository.save(mapper.dtoToEntity(menuDto)).getId();
    }

    @Transactional(readOnly = true)
    public List<MenuDto> getMenus(boolean includeExpiredMenu,
                                  MenuCategory menuCategory,
                                  String menuName) {
        return null;
    }

    @Transactional(readOnly = true)
    public MenuDto getMenu(int id) {
        if ( id < 0 ) throw new InvalidInputException( "잘못된 메뉴 ID : " + id );

        Menu foundMenu = menuRepository.findById(id)
                .orElseThrow( () -> new NotFoundException( "없는 메뉴 ID : " + id ) );

        log.debug("메뉴 조회 성공. menuID = {}", id);

        return mapper.entityToDto(foundMenu);
    }
}
