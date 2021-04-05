package realimpact.moonbug.web;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import realimpact.moonbug.domain.menu.MenuCategory;
import realimpact.moonbug.domain.menu.MenuSize;
import realimpact.moonbug.exception.InvalidInputException;
import realimpact.moonbug.service.MenuService;
import realimpact.moonbug.web.dto.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@NoArgsConstructor
public class MenuApiController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/api/v1/menus")
    public int createMenu(@RequestBody MenuCreateRequestDto menuCreateRequestDto) {
        return menuService.createMenu(menuCreateRequestDto);
    }

    @PutMapping("/api/v1/menus/{menu_id}")
    public int updateMenu(
            @PathVariable("menu_id") int menuId,
            @RequestBody MenuUpdateRequestDto menuUpdateRequestDto) {
        return menuService.updateMenu(menuId, menuUpdateRequestDto);
    }

    @DeleteMapping("/api/v1/menus/{menu_id}")
    public void deleteMenu(@PathVariable("menu_id") int menuId) {
        menuService.deleteMenu(menuId);
    }

    @PostMapping("/api/v1/menus/{menu_id}/size")
    public int addMenuSizePolicy(
            @PathVariable("menu_id") int menuId,
            @RequestBody MenuSizePolicyCreateRequestDto menuSizePolicyCreateRequestDto) {
        return menuService.addMenuSizePolicy(menuId, menuSizePolicyCreateRequestDto);
    }

    @PutMapping("/api/v1/menus/{menu_id}/size/{menu_size}")
    public int updateMenuSizePolicy(
            @PathVariable("menu_id") int menuId,
            @PathVariable("menu_size") MenuSize menuSize,
            @RequestBody MenuSizePolicyUpdateRequestDto menuSizePolicyUpdateRequestDto) {
        return menuService.updateMenuSizePolicy(menuId, menuSize, menuSizePolicyUpdateRequestDto);
    }

    @GetMapping("/api/v1/menus/{menu_id}")
    public MenuResponseDto getMenu(
            @PathVariable("menu_id") Integer menuId) {
        return menuService.getMenu(menuId);
    }

    @GetMapping("/api/v1/menus/")
    public List<MenuResponseDto> getMenus(
            @RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "q", required = false) String queryString) {

        String decodedQryStr;
        try {
            decodedQryStr = URLDecoder.decode(queryString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e){
            throw new InvalidInputException("조회조건에 유효하지 않은 값이 들어가 있습니다!", e);
        }

        String qName = null;
        boolean qIncludeFutureMenu = false;
        boolean qIncludeExpiredMenu = false;
        MenuCategory qMenuCategory = null;

        String [] querys = decodedQryStr.split(",");
        for (String q : querys) {
            String [] keyVal = q.split("=" );
            if ( "name".equals(keyVal[0]) )
                qName = keyVal[1];
            else if ( "menu-category".equals(keyVal[0]) )
                qMenuCategory = MenuCategory.valueOf(keyVal[1]);
            else if ( "include-future-menu".equals(keyVal[0]) )
                qIncludeFutureMenu = Boolean.parseBoolean(keyVal[1]);
            else if ( "include-expired-menu".equals(keyVal[0]) )
                qIncludeExpiredMenu = Boolean.parseBoolean(keyVal[1]);
        }

        return menuService.getMenus(offset, limit, qName, qMenuCategory, qIncludeFutureMenu, qIncludeExpiredMenu);
    }
}