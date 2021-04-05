package realimpact.moonbug.domain.menu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MenuSizePolicyRepositoryTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuSizePolicyRepository menuSizePolicyRepository;

    @Before
    public void setUp() {
        Menu mocha = TestMenuEntityFactory.createMocha();
        menuRepository.save(mocha);
    }

    @Test
    public void findWithMenuIdOrderByMenuSize() {
        Menu menu = menuRepository.findByName("모카").get();

        List<MenuSizePolicy> menuSizePolicies =
                menuSizePolicyRepository.findByMenuIdOrderByMenuSize(menu.getId());

        assertThat( menuSizePolicies.size() ).isEqualTo(4);

        assertThat( menuSizePolicies.get(0).getMenuSize() ).isEqualTo(MenuSize.SHORT);
        assertThat( menuSizePolicies.get(1).getMenuSize() ).isEqualTo(MenuSize.TALL);
        assertThat( menuSizePolicies.get(2).getMenuSize() ).isEqualTo(MenuSize.GRANDE);
        assertThat( menuSizePolicies.get(3).getMenuSize() ).isEqualTo(MenuSize.VENTI);
    }
}
