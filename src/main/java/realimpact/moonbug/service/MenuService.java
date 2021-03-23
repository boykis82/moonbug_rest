package realimpact.moonbug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realimpact.moonbug.domain.menu.MenuRepository;
import realimpact.moonbug.web.dto.MenuDto;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    /*
    @Transactional
    public int addMenu(MenuDto menuSaveRequestDto) {
        return menuRepository.save(menuSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public int addMenuIngredient(MenuDto menuIngredientSaveRequestDto) {
        return menuRepository.save(menuSaveRequestDto.toEntity()).getId();
    }*/
}
