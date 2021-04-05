package realimpact.moonbug.domain.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestMenuEntityFactory {

    private TestMenuEntityFactory() {}

    public static Menu createAmericano() {
        Menu americano = Menu.builder()
                .name("아메리카노")
                .content("기본적인 커피 메뉴입니다.")
                .startDate(LocalDate.now())
                .expireDate(LocalDate.of(9999,12,31))
                .menuCategory(MenuCategory.DRINK)
                .build();

        americano.addMenuSizePolicy( createMenuSizePolicy(americano, MenuSize.GRANDE, 5100, 20.0) );
        americano.addMenuSizePolicy( createMenuSizePolicy(americano, MenuSize.VENTI, 5600, 30.0) );

        MenuSizePolicy tamsp = createMenuSizePolicy(americano, MenuSize.TALL, 4600, 10.0);
        tamsp.addMenuIngredient(
                MenuIngredient.builder()
                    .amountWithUnit("10g")
                    .ingredientName("설탕").menuSizePolicy(tamsp).build());
        tamsp.addMenuIngredient(
                MenuIngredient.builder()
                    .amountWithUnit("0.5ml")
                    .ingredientName("시럽").menuSizePolicy(tamsp).build());

        americano.addMenuSizePolicy( tamsp );

        return americano;
    }

    public static Menu createAmericano2() {
        Menu americano = Menu.builder()
                .name("2아메리카노2")
                .content("기본적인 커피 메뉴2입니다.")
                .startDate(LocalDate.now())
                .expireDate(LocalDate.of(9999,12,31))
                .menuCategory(MenuCategory.DRINK)
                .build();

        americano.addMenuSizePolicy( createMenuSizePolicy(americano, MenuSize.TALL, 4600, 15) );
        americano.addMenuSizePolicy( createMenuSizePolicy(americano, MenuSize.GRANDE, 5100, 16) );
        americano.addMenuSizePolicy( createMenuSizePolicy(americano, MenuSize.VENTI, 5600, 17) );

        return americano;
    }

    public static Menu createLatte() {
        Menu latte = Menu.builder().name("라떼")
                .content("우유가 들어간 기본적인 커피 메뉴입니다.")
                .startDate(LocalDate.now())
                .expireDate(LocalDate.of(9999,12,31))
                .menuCategory(MenuCategory.DRINK)
                .build();

        latte.addMenuSizePolicy( createMenuSizePolicy(latte, MenuSize.SHORT, 4600, 200) );
        latte.addMenuSizePolicy( createMenuSizePolicy(latte, MenuSize.TALL, 5100, 300) );
        latte.addMenuSizePolicy( createMenuSizePolicy(latte, MenuSize.GRANDE, 5600, 400) );

        return latte;
    }

    public static Menu createMocha() {
        Menu mocha = Menu.builder().name("모카")
                .content("초카가 들어간 기본적인 커피 메뉴입니다.")
                .startDate(LocalDate.now())
                .expireDate(LocalDate.of(9999,12,31))
                .menuCategory(MenuCategory.DRINK)
                .build();

        mocha.addMenuSizePolicy( createMenuSizePolicy(mocha, MenuSize.TALL, 5600, 200) );
        mocha.addMenuSizePolicy( createMenuSizePolicy(mocha, MenuSize.VENTI, 6600, 400) );
        mocha.addMenuSizePolicy( createMenuSizePolicy(mocha, MenuSize.GRANDE, 6100, 300) );
        mocha.addMenuSizePolicy( createMenuSizePolicy(mocha, MenuSize.SHORT, 5100, 100) );

        return mocha;
    }

    private static String getRandomMenuName(int seed) {
        switch(seed % 4) {
            case 0: return "아메리카노" + String.valueOf(seed);
            case 1: return "라떼" + String.valueOf(seed);
            case 2: return "허니자몽블랙티" + String.valueOf(seed);
            case 3: return "프라프치노" + String.valueOf(seed);
        }
        throw new RuntimeException("있을 수 없어.");
    }

    private static LocalDate getRandomExpireDate(int seed) {
        switch(seed % 4) {
            case 0: return LocalDate.of(9999, 12, 31);
            case 1: return LocalDate.of(2021, 6, 1);
            case 2: return LocalDate.of(2020, 12, 1);
            case 3: return LocalDate.now();
        }
        throw new RuntimeException("있을 수 없어.");
    }

    private static LocalDate getRandomStartDate(int seed) {
        switch(seed % 4) {
            case 0: return LocalDate.now();
            case 1: return LocalDate.of(2020, 6, 1);
            case 2: return LocalDate.of(2019, 12, 1);
            case 3: return LocalDate.of(2019, 1, 1);
        }
        throw new RuntimeException("있을 수 없어.");
    }

    private static MenuCategory getRandomMenuCategory(int seed) {
        switch(seed % 3) {
            case 0: return MenuCategory.DRINK;
            case 1: return MenuCategory.FOOD;
            case 2: return MenuCategory.PROD;
        }
        throw new RuntimeException("있을 수 없어.");
    }

    public static List<Menu> createManyMenu(int count) {
        ArrayList<Menu> menus = new ArrayList<Menu>();

        for( int i = 0 ; i < count ; ++i ) {
            Menu menu = Menu.builder()
                    .name( getRandomMenuName(i) )
                    .content("...")
                    .startDate( getRandomStartDate(i) )
                    .expireDate( getRandomExpireDate(i) )
                    .menuCategory( getRandomMenuCategory(i) )
                    .build();

            menu.addMenuSizePolicy(MenuSizePolicy.builder()
                    .menuSize(MenuSize.TALL)
                    .calories(100)
                    .price(4600)
                    .build());

            menu.addMenuSizePolicy(MenuSizePolicy.builder()
                    .menuSize(MenuSize.GRANDE)
                    .calories(120)
                    .price(5100)
                    .build());

            menus.add(menu);
        }

        return menus;
    }

    public static MenuSizePolicy createMenuSizePolicy(Menu menu, MenuSize menuSize, int price, double calories) {
        return MenuSizePolicy.builder()
                .menuSize(menuSize)
                .price(price)
                .calories(calories)
                .menu(menu)
                .build();
    }
}
