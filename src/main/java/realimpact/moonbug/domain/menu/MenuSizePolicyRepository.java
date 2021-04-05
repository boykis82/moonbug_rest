package realimpact.moonbug.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuSizePolicyRepository extends JpaRepository<MenuSizePolicy, Integer> {
    List<MenuSizePolicy> findByMenuIdOrderByMenuSize(int menuId);
}
