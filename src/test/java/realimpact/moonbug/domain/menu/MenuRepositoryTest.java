package realimpact.moonbug.domain.menu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    @Before
    public void setUp() {
        Menu mocha = TestMenuEntityFactory.createMocha();
        menuRepository.save(mocha);
    }

    @After
    public void tearDown() {
        menuRepository.deleteAll();
    }

    @Test
    //@Transactional  // lazy loading 시 그 전에 영속성이 끝나버리기 때문에
    public void testInsertMenuOnly() {
        Menu latte = TestMenuEntityFactory.createLatte();
        menuRepository.save(latte);

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList.size()).isEqualTo(2);

        Menu insertedLatte = menuList.get(1);
        assertTrue(compareMenu(latte, insertedLatte));

        assertThat(insertedLatte.getMenuSizePolicies().size()).isEqualTo(3);
    }

    @Test
    public void testMenuSizePolicy() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        menuRepository.save(americano);

        Menu insertedAmericano = menuRepository.findByName("아메리카노").get();

        MenuSizePolicy tallAmericanoPolicy = insertedAmericano.getMenuSizePolicy(MenuSize.TALL).get();
        assertThat(tallAmericanoPolicy.getPrice()).isEqualTo(4600);

        MenuSizePolicy grandeAmericanoPolicy = insertedAmericano.getMenuSizePolicy(MenuSize.GRANDE).get();
        assertThat(grandeAmericanoPolicy.getPrice()).isEqualTo(5100);

        Optional<MenuSizePolicy> shortAmericanoPolicy = insertedAmericano.getMenuSizePolicy(MenuSize.SHORT);
        assertFalse(shortAmericanoPolicy.isPresent());
    }

    @Test
    public void testInsertMenuTwice() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        Menu latte = TestMenuEntityFactory.createLatte();

        List<Menu> menus = new ArrayList<Menu>();
        menus.add(americano);
        menus.add(latte);

        menuRepository.saveAll(menus);

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList.size()).isEqualTo(3);

        Menu insertedAmericano = menuList.get(1);
        assertTrue(compareMenu(americano, insertedAmericano));

        Menu insertedLatte = menuList.get(2);
        assertTrue(compareMenu(latte, insertedLatte));
    }

    @Test
    public void testFindByName() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        menuRepository.save(americano);

        Optional<Menu> insertedAmericano = menuRepository.findByName("아메리카노");
        assertTrue(compareMenu(americano, insertedAmericano.get()));
    }

    @Test
    public void testGetIngredients() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        menuRepository.save(americano);

        Menu insertedAmericano = menuRepository.findByName("아메리카노").get();
        MenuSizePolicy americanoTallSizePolicy = insertedAmericano.getMenuSizePolicy(MenuSize.TALL).get();
        assertThat(americanoTallSizePolicy.getMenuIngredients().size()).isEqualTo(2);

        List<MenuIngredient> americanoTallSizeIngredients = americanoTallSizePolicy.getMenuIngredients();
        Iterator<MenuIngredient> it = americanoTallSizeIngredients.iterator();
        while( it.hasNext() ) {
            MenuIngredient menuIngredient = it.next();
            assertTrue( menuIngredient.getIngredientName().equals("설탕")
                    || menuIngredient.getIngredientName().equals("시럽") );
        }
    }

    @Test
    public void testFindByNameLike() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        Menu americano2 = TestMenuEntityFactory.createAmericano2();
        Menu latte = TestMenuEntityFactory.createLatte();

        List<Menu> menus = new ArrayList<Menu>();
        menus.add(americano);
        menus.add(americano2);
        menus.add(latte);

        menuRepository.saveAll(menus);

        assertThat(menuRepository.findByNameContaining("아메리카노").size()).isEqualTo(2);
        assertThat(menuRepository.findByNameContaining("라떼").size()).isEqualTo(1);
    }

    @Test
    public void testExpireMenu() {
        Menu americano = TestMenuEntityFactory.createAmericano();
        menuRepository.save(americano);

        Menu insertedAmericano = menuRepository.findByName("아메리카노").get();
        insertedAmericano.expire(LocalDate.now());
        menuRepository.save(insertedAmericano);

        Menu updatedAmericano = menuRepository.findByName("아메리카노").get();

        assertThat(LocalDate.now()).isEqualTo(updatedAmericano.getExpireDate());

    }

    private boolean compareMenu(Menu menu1, Menu menu2) {
        return  menu1.getName().equals(menu2.getName()) &&
                menu1.getMenuSizePolicies().size() == menu2.getMenuSizePolicies().size() &&
                menu1.getStartDate().equals(menu2.getStartDate()) &&
                menu1.getExpireDate().equals(menu2.getExpireDate()) &&
                menu1.getMenuCategory().equals(menu2.getMenuCategory());
    }
}
