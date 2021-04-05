package realimpact.moonbug.domain.menu;

import static com.querydsl.core.types.ExpressionUtils.and;
import static realimpact.moonbug.domain.menu.QMenu.menu;
import static realimpact.moonbug.domain.menu.QMenuSizePolicy.menuSizePolicy;
import static realimpact.moonbug.domain.menu.QMenuIngredient.menuIngredient;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import realimpact.moonbug.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<Menu> findBy(int offset,
                             int limit,
                             String name,
                             MenuCategory menuCategory,
                             boolean includeFutureMenu,
                             boolean includeExpiredMenu) {
        QueryResults<Menu> result = queryFactory.selectFrom(menu)
                .where( nameCondition(name),
                        categoryCondition(menuCategory),
                        startDateCondition(includeFutureMenu, LocalDate.now()),
                        expireDateCondition(includeExpiredMenu, LocalDate.now()))
                .leftJoin(menu.menuSizePolicies, menuSizePolicy)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy( menu.menuCategory.asc(), menu.name.asc() )
                .fetchResults();

        return result.getResults();
    }

    public Menu getMenuWithSizePolicy(int menuId) {
        return queryFactory.selectFrom(menu)
                .leftJoin(menu.menuSizePolicies, menuSizePolicy)
                .fetchJoin()
                .where(menu.id.eq(menuId))
                .orderBy(menuSizePolicy.menuSize.asc())
                .fetchOne();
    }

    private BooleanExpression nameCondition(String name) {
        if (name == null || name.length() == 0)
            return null;
        return menu.name.contains(name);
    }

    private BooleanExpression categoryCondition(MenuCategory menuCategory) {
        if( menuCategory == null )
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
