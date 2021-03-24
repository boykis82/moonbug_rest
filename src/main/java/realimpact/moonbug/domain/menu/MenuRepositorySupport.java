package realimpact.moonbug.domain.menu;

import static com.querydsl.core.types.ExpressionUtils.and;
import static realimpact.moonbug.domain.menu.QMenu.menu;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import realimpact.moonbug.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<Menu> findBy(String name,
                             boolean findAllCategory,
                             MenuCategory menuCategory,
                             boolean includeFutureMenu,
                             boolean includeExpiredMenu) {

        if( !findAllCategory && menuCategory == MenuCategory.NONE )
            throw new InvalidInputException("일부 카테고리 조회인데 카테고리 지정안되어 있음!");

        return queryFactory.selectFrom(menu)
                .where( nameCondition(name),
                        categoryCondition(findAllCategory, menuCategory),
                        startDateCondition(includeFutureMenu, LocalDate.now()),
                        expireDateCondition(includeExpiredMenu, LocalDate.now()))
                .orderBy( menu.menuCategory.desc(), menu.name.asc() )
                .fetch();
    }

    private BooleanExpression nameCondition(String name) {
        if (name.length() == 0)
            return null;
        return menu.name.contains(name);
    }

    private BooleanExpression categoryCondition(boolean findAllCategory, MenuCategory menuCategory) {
        if( findAllCategory )
            return null;
        return menu.menuCategory.eq(menuCategory);
    }

    private BooleanExpression startDateCondition(boolean includeFutureMenu, LocalDate strdDate) {
        if( includeFutureMenu )
            return null;
        return menu.startDate.loe(strdDate);
    }

    private BooleanExpression expireDateCondition(boolean includeExpiredMenu, LocalDate strdDate) {
        if( includeExpiredMenu )
            return null;
        return menu.expireDate.goe(strdDate);
    }
}
