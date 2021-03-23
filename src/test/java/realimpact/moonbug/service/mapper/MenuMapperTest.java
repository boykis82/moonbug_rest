package realimpact.moonbug.service.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import realimpact.moonbug.domain.menu.Menu;
import realimpact.moonbug.domain.menu.MenuIngredient;
import realimpact.moonbug.domain.menu.MenuSizePolicy;
import realimpact.moonbug.domain.menu.TestMenuFactory;
import realimpact.moonbug.web.dto.MenuDto;
import realimpact.moonbug.web.dto.MenuIngredientDto;
import realimpact.moonbug.web.dto.MenuSizePolicyDto;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        MenuMapperImpl.class,
        MenuSizePolicyMapperImpl.class,
        MenuIngredientMapperImpl.class
})
// mapper들 강제 주입 위함
public class MenuMapperTest {

    @Autowired
    private MenuMapper mapper;

    @Test
    public void testEntityToDto() {
        Menu entity = TestMenuFactory.createAmericano();
        MenuDto dto = mapper.entityToDto(entity);

        assertTrue( compareMenu(entity, dto) );
    }

    @Test
    public void testDtoToEntity() {
        MenuDto dto = mapper.entityToDto(TestMenuFactory.createAmericano());
        Menu entity = mapper.dtoToEntity(dto);

        assertTrue( compareMenu(entity, dto) );
    }

    private boolean compareMenu(Menu entity, MenuDto dto) {
        if (    !entity.getName().equals(dto.getName()) ||
                !entity.getContent().equals(dto.getContent()) ||
                entity.getMenuSizePolicies().size() != dto.getMenuSizePolicies().size())
            return false;

        if ( !compareMenuSizePolicies(entity.getMenuSizePolicies(), dto.getMenuSizePolicies()) )
            return false;

        return true;
    }

    private boolean compareMenuSizePolicies(
            List<MenuSizePolicy> menuSizePolicyEntities,
            List<MenuSizePolicyDto> menuSizePolicyDtos) {
        if( menuSizePolicyDtos.size() != menuSizePolicyEntities.size() )
            return false;

        for( int i = 0; i < menuSizePolicyDtos.size() ; ++i) {
            if ( !compareMenuSizePolicy( menuSizePolicyEntities.get(i), menuSizePolicyDtos.get(i) ) )
                return false;
        }

        return true;
    }

    private boolean compareMenuSizePolicy(MenuSizePolicy entity, MenuSizePolicyDto dto) {
        if ( entity.getPrice() != dto.getPrice() ||
             entity.getCalories() != dto.getCalories() ||
             entity.getMenuSize() != dto.getMenuSize() ||
             entity.getMenuIngredients().size() != dto.getMenuIngredients().size() )
            return false;

        if ( !compareMenuIngredients(entity.getMenuIngredients(), dto.getMenuIngredients()) )
            return false;

        return true;
    }

    private boolean compareMenuIngredients(
            List<MenuIngredient> menuIngredientsEntities,
            List<MenuIngredientDto> menuIngredientsDtos) {
        if( menuIngredientsEntities.size() != menuIngredientsDtos.size() )
            return false;

        for( int i = 0; i < menuIngredientsDtos.size() ; ++i) {
            if ( !compareMenuIngredient( menuIngredientsEntities.get(i), menuIngredientsDtos.get(i) ) )
                return false;
        }

        return true;
    }

    private boolean compareMenuIngredient(MenuIngredient entity, MenuIngredientDto dto) {
        return  entity.getIngredientName().equals(dto.getIngredientName()) &&
                entity.getAmountWithUnit().equals(dto.getAmountWithUnit());
    }
}