package realimpact.moonbug.service.mapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        MenuCreateMapperImpl.class,
        MenuSizePolicyCreateMapperImpl.class,
        MenuIngredientCreateMapperImpl.class
        /*MenuMapperImpl.class,
        MenuSizePolicyMapperImpl.class,
        MenuIngredientMapperImpl.class*/
})
// mapper들 강제 주입 위함
public class MenuMapperTest {

    @Autowired
    private MenuCreateMapper mapper;

    /*
    @Test
    public void testEntityToDto() {
        Menu entity = TestMenuEntityFactory.createAmericano();
        MenuDto dto = mapper.entityToDto(entity);

        assertTrue( compareMenu(entity, dto) );
        assertNotNull( entity.getMenuSizePolicies().get(0).getMenu() );
        assertNull( dto.getMenuSizePolicies().get(0).getMenu() );
    }

     */

    /*
    @Test
    public void testDtoToEntity() {
        MenuDto dto = mapper.entityToDto(TestMenuEntityFactory.createAmericano());
        Menu entity = mapper.dtoToEntity(dto);

        assertTrue( compareMenu(entity, dto) );
        assertNull( dto.getMenuSizePolicies().get(0).getMenu() );
        assertNotNull( entity.getMenuSizePolicies().get(0).getMenu() );
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

        if ( entity.getMenu() == null || dto.getMenu() == null )

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
*/
}
