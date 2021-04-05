package realimpact.moonbug.domain.menu;

import org.junit.After;
import org.junit.AfterClass;
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

    private static MenuRepository staticMenuRepository = null;

    @Autowired
    private MenuRepositorySupport menuRepositorySupport;

    // 매 테스트마다 너무 많은 insert가 일어나 느려서 꼼수 부림.
    @Before
    public void setup() {
        if( staticMenuRepository == null) {
            staticMenuRepository = menuRepository;
            staticMenuRepository.saveAll(TestMenuEntityFactory.createManyMenu(100));
        }
    }

    @AfterClass
    public static void classClean() {
        staticMenuRepository.deleteAll();
    }

    @Test
    public void testFindWithName() {
        List<Menu> menus;
        menus = menuRepositorySupport.findBy(0, 100, "아메리카노", null, true,true);

        assertThat( menus.size() ).isEqualTo(25);
        assertThat( menus.get(0).getName() ).contains("아메리카노");

        menus = menuRepositorySupport.findBy(0, 12, "아메리카노", null, true,true);
        assertThat( menus.size() ).isEqualTo(12);
        menus = menuRepositorySupport.findBy(12, 12, "아메리카노", null, true,true);
        assertThat( menus.size() ).isEqualTo(12);
        menus = menuRepositorySupport.findBy(24, 12, "아메리카노", null, true,true);
        assertThat( menus.size() ).isEqualTo(1);
    }

    @Test
    public void testFindWithNameAndCategory() {
        List<Menu> menus;
        menus = menuRepositorySupport.findBy(0, 100, "아메리카노", MenuCategory.DRINK,true, true);

        assertThat( menus.size() ).isEqualTo(9);
        assertThat( menus.get(0).getName() ).contains("아메리카노");
        assertThat( menus.get(0).getMenuCategory() ).isEqualTo( MenuCategory.DRINK );
    }

    @Test
    public void testFindWithDate() {
        List<Menu> menus;
        menus = menuRepositorySupport.findBy(0, 100, null, null,false, false);

        assertThat( menus.size() ).isEqualTo(75);
    }

    @Test
    public void testGetMenuWithSizePolicy() {
        Menu menu = menuRepositorySupport.getMenuWithSizePolicy(1);
        assertThat( menu.getName() ).contains("아메리카노");
        assertThat( menu.getMenuSizePolicies().size() ).isEqualTo(2);
    }
}
