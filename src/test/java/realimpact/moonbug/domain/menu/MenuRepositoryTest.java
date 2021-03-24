package realimpact.moonbug.domain.menu;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    @After
    public void tearDown() {
        menuRepository.deleteAll();
    }

    @Test
    @Transactional  // lazy loading 시 그 전에 영속성이 끝나버리기 때문에
    public void testInsertMenuOnly() {
        Menu latte = TestMenuFactory.createLatte();
        menuRepository.save(latte);

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList.size()).isEqualTo(1);

        Menu insertedLatte = menuRepository.findById(menuList.get(0).getId()).get();
        assertTrue(compareMenu(latte, insertedLatte));

        assertThat(insertedLatte.getMenuSizePolicies().size()).isEqualTo(3);

        assertEquals(insertedLatte.getId(), insertedLatte.getMenuSizePolicies().get(0).getMenu().getId());
    }

    @Test
    @Transactional
    public void testMenuSizePolicy() {
        Menu americano = TestMenuFactory.createAmericano();
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
    @Transactional
    public void testInsertMenuTwice() {
        Menu americano = TestMenuFactory.createAmericano();
        Menu latte = TestMenuFactory.createLatte();

        List<Menu> menus = new ArrayList<Menu>();
        menus.add(americano);
        menus.add(latte);

        menuRepository.saveAll(menus);

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList.size()).isEqualTo(2);

        Menu insertedAmericano = menuList.get(0);
        assertTrue(compareMenu(americano, insertedAmericano));

        Menu insertedLatte = menuList.get(1);
        assertTrue(compareMenu(latte, insertedLatte));
    }

    @Test
    @Transactional
    public void testFindByName() {
        Menu americano = TestMenuFactory.createAmericano();
        menuRepository.save(americano);

        Optional<Menu> insertedAmericano = menuRepository.findByName("아메리카노");
        assertTrue(compareMenu(americano, insertedAmericano.get()));
    }

    @Test
    @Transactional
    public void testGetIngredients() {
        Menu americano = TestMenuFactory.createAmericano();
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
    @Transactional
    public void testFindByNameLike() {
        Menu americano = TestMenuFactory.createAmericano();
        Menu americano2 = TestMenuFactory.createAmericano2();
        Menu latte = TestMenuFactory.createLatte();

        List<Menu> menus = new ArrayList<Menu>();
        menus.add(americano);
        menus.add(americano2);
        menus.add(latte);

        menuRepository.saveAll(menus);

        assertThat(menuRepository.findByNameContaining("아메리카노").size()).isEqualTo(2);
        assertThat(menuRepository.findByNameContaining("라떼").size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testExpireMenu() {
        Menu americano = TestMenuFactory.createAmericano();
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
