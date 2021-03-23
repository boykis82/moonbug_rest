package realimpact.moonbug.domain.menu;

import java.time.LocalDate;

public class TestMenuFactory {

    private TestMenuFactory() {}

    public static Menu createAmericano() {
        Menu americano = Menu.builder()
                .name("아메리카노")
                .content("기본적인 커피 메뉴입니다.")
                .startDate(LocalDate.now())
                .build();

        MenuSizePolicy tamsp = createMenuSizePolicy(MenuSize.TALL, 4600, 10.0);
        tamsp.addMenuIngredient(
                MenuIngredient.builder()
                    .amountWithUnit("10g")
                    .ingredientName("설탕").build());
        tamsp.addMenuIngredient(
                MenuIngredient.builder()
                    .amountWithUnit("0.5ml")
                    .ingredientName("시럽").build());

        americano.addMenuSizePolicy( tamsp );
        americano.addMenuSizePolicy( createMenuSizePolicy(MenuSize.GRANDE, 5100, 20.0) );
        americano.addMenuSizePolicy( createMenuSizePolicy(MenuSize.VENTI, 5600, 30.0) );

        return americano;
    }

    public static Menu createAmericano2() {
        Menu americano = Menu.builder()
                .name("2아메리카노2")
                .content("기본적인 커피 메뉴2입니다.")
                .startDate(LocalDate.now())
                .build();

        americano.addMenuSizePolicy( createMenuSizePolicy(MenuSize.TALL, 4600, 15) );
        americano.addMenuSizePolicy( createMenuSizePolicy(MenuSize.GRANDE, 5100, 16) );
        americano.addMenuSizePolicy( createMenuSizePolicy(MenuSize.VENTI, 5600, 17) );

        return americano;
    }

    public static Menu createLatte() {
        Menu latte = Menu.builder().name("라떼")
                .content("우유가 들어간 기본적인 커피 메뉴입니다.")
                .startDate(LocalDate.now())
                .build();

        latte.addMenuSizePolicy( createMenuSizePolicy(MenuSize.SHORT, 4600, 200) );
        latte.addMenuSizePolicy( createMenuSizePolicy(MenuSize.TALL, 5100, 300) );
        latte.addMenuSizePolicy( createMenuSizePolicy(MenuSize.GRANDE, 5600, 400) );

        return latte;
    }

    public static MenuSizePolicy createMenuSizePolicy(MenuSize menuSize, int price, double calories) {
        return MenuSizePolicy.builder()
                .menuSize(menuSize)
                .price(price)
                .calories(calories)
                .build();
    }
}
