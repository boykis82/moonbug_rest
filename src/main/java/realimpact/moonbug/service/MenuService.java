package realimpact.moonbug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realimpact.moonbug.domain.menu.*;
import realimpact.moonbug.exception.InvalidInputException;
import realimpact.moonbug.exception.NotFoundException;
import realimpact.moonbug.service.mapper.*;
import realimpact.moonbug.web.dto.*;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuRepositorySupport menuRepositorySupport;
    private final MenuSizePolicyRepository menuSizePolicyRepository;
    private final MenuCreateMapper menuCreateMapper;
    private final MenuSizePolicyCreateMapper menuSizePolicyCreateMapper;
    private final MenuResponseMapper menuResponseMapper;
    private final MenuSizePolicyResponseMapper menuSizePolicyResponseMapper;

    @Autowired
    public MenuService(MenuRepository menuRepository,
                       MenuRepositorySupport menuRepositorySupport,
                       MenuSizePolicyRepository menuSizePolicyRepository,
                       MenuCreateMapper menuCreateMapper,
                       MenuSizePolicyCreateMapper menuSizePolicyCreateMapper,
                       MenuResponseMapper menuResponseMapper,
                       MenuSizePolicyResponseMapper menuSizePolicyResponseMapper) {
        this.menuCreateMapper = menuCreateMapper;
        this.menuSizePolicyCreateMapper = menuSizePolicyCreateMapper;
        this.menuRepository = menuRepository;
        this.menuRepositorySupport = menuRepositorySupport;
        this.menuSizePolicyRepository = menuSizePolicyRepository;
        this.menuResponseMapper = menuResponseMapper;
        this.menuSizePolicyResponseMapper = menuSizePolicyResponseMapper;
    }

    @Transactional
    public int createMenu(MenuCreateRequestDto menuCreateRequestDto) {
        Menu createdMenu = menuRepository.save(
                                menuCreateMapper.dtoToEntity(menuCreateRequestDto));
        return createdMenu.getId();
    }

    @Transactional
    public int updateMenu(int menuId, MenuUpdateRequestDto menuUpdateRequestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow( () -> new NotFoundException("없는 메뉴입니다. " + menuId) );
        menu.setName( menuUpdateRequestDto.getName() );
        menu.setContent( menuUpdateRequestDto.getContent() );
        menu.setStartDate( menuUpdateRequestDto.getStartDate() );
        menu.setExpireDate( menuUpdateRequestDto.getExpireDate() );
        menu.setMenuCategory( menuUpdateRequestDto.getMenuCategory() );
        return menuRepository.save(menu).getId();
    }

    @Transactional
    public void deleteMenu(int menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow( () -> new NotFoundException("없는 메뉴입니다. " + menuId) );
        menuRepository.delete(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenus(int offset,
                                          int limit,
                                          String menuName,
                                          MenuCategory menuCategory,
                                          boolean includeFutureMenu,
                                          boolean includeExpiredMenu) {
        List<Menu> menuEntities = menuRepositorySupport.findBy(offset, limit, menuName, menuCategory, includeFutureMenu, includeExpiredMenu);
        return menuResponseMapper.entityListToDtoList(menuEntities);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(int menuId) {
        Menu foundMenu = menuRepositorySupport.getMenuWithSizePolicy(menuId);
        if( foundMenu == null )
            throw new NotFoundException("Menu (id = " + menuId + ") not exists!");

        log.debug("메뉴 조회 성공. menuID = {}", menuId);

        return menuResponseMapper.entityToDto(foundMenu);
    }

    @Transactional
    public int addMenuSizePolicy(int menuId, MenuSizePolicyCreateRequestDto menuSizePolicyCreateRequestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow( () -> new NotFoundException("없는 메뉴입니다. " + menuId) );

        MenuSizePolicy menuSizePolicy = menuSizePolicyCreateMapper.dtoToEntity(menuSizePolicyCreateRequestDto);
        menu.addMenuSizePolicy(menuSizePolicy);
        menuSizePolicy.setMenu(menu);
        return menuRepository.save(menu).getId();
    }

    @Transactional
    public int updateMenuSizePolicy(int menuId, MenuSize menuSize, MenuSizePolicyUpdateRequestDto menuSizePolicyUpdateRequestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow( () -> new NotFoundException("없는 메뉴입니다. menuId = " + menuId) );
        MenuSizePolicy menuSizePolicy = menu.getMenuSizePolicy(menuSize)
                .orElseThrow( () -> new NotFoundException("없는 사이즈입니다. menuId = " + menuId + " , menuSize = " + menuSize) );

        menuSizePolicy.setCalories( menuSizePolicyUpdateRequestDto.getCalories() );
        menuSizePolicy.setPrice( menuSizePolicyUpdateRequestDto.getPrice() );
        return menuRepository.save(menu).getId();
    }
}
