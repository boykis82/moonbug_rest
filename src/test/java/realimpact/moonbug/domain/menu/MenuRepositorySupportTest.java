package realimpact.moonbug.domain.menu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuRepositorySupportTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuRepositorySupport menuRepositorySupport;

    @Before
    public void setup() {
        menuRepository.saveAll( TestMenuFactory.createManyMenu(100 ) );
    }

    @After
    public void clean() {
        menuRepository.deleteAllInBatch();
    }

    @Test
    public void testFindWithName() {
        List<Menu> menus;
        menus =
                menuRepositorySupport.findBy("아메리카노", true, MenuCategory.NONE,true, true);

        assertEquals(25, menus.size() );
        assertThat( menus.get(0).getName() ).contains("아메리카노");
    }

    @Test
    public void testFindWithNameAndCategory() {
        List<Menu> menus;
        menus = menuRepositorySupport.findBy("아메리카노", false, MenuCategory.DRINK,true, true);

        assertEquals(9, menus.size() );
        assertThat( menus.get(0).getName() ).contains("아메리카노");
        assertThat( menus.get(0).getMenuCategory() ).isEqualTo( MenuCategory.DRINK );
    }

    @Test
    public void testFindWithDate() {
        List<Menu> menus;
        menus = menuRepositorySupport.findBy("", true, MenuCategory.NONE,false, false);

        assertEquals(75, menus.size() );
    }
}
