package kr.project.userfeature.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.project.userfeature.core.jpa.template.PagingRepositoryTemplate;
import kr.project.userfeature.user.domain.entity.QUserEntity;
import kr.project.userfeature.user.dto.UserResponse;
import kr.project.userfeature.user.dto.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements PagingRepositoryTemplate<UserResponse, UserQuery> {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Long findCountByQuery(UserQuery query) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(QUserEntity.userEntity)
                .where(whereClause(query))
                .fetchOne();
    }

    @Override
    public List<UserResponse> findAllByQueryWithPaging(UserQuery query) {
        return jpaQueryFactory.select(Projections.fields(
                    UserResponse.class,
                    QUserEntity.userEntity.userId,
                    QUserEntity.userEntity.email,
                    QUserEntity.userEntity.phoneNumber,
                    QUserEntity.userEntity.nickname,
                    QUserEntity.userEntity.name,
                    QUserEntity.userEntity.enabled,
                    QUserEntity.userEntity.roleType,
                    QUserEntity.userEntity.createdAt,
                    QUserEntity.userEntity.createdBy,
                    QUserEntity.userEntity.updatedAt,
                    QUserEntity.userEntity.updatedBy
                ))
                .from(QUserEntity.userEntity)
                .where(whereClause(query))
                .offset(query.getPageable().getOffset())
                .limit(query.getPageable().getPageSize())
                .fetch();
    }

    private BooleanBuilder whereClause(UserQuery query) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(query.getEmail())) {
            booleanBuilder.and(QUserEntity.userEntity.email.contains(query.getEmail()));
        }

        if(StringUtils.hasText(query.getPhoneNumber())) {
            booleanBuilder.and(QUserEntity.userEntity.phoneNumber.contains(query.getPhoneNumber()));
        }

        if(StringUtils.hasText(query.getNickname())) {
            booleanBuilder.and(QUserEntity.userEntity.nickname.contains(query.getNickname()));
        }

        if(StringUtils.hasText(query.getName())) {
            booleanBuilder.and(QUserEntity.userEntity.name.contains(query.getName()));
        }

        if(Objects.nonNull(query.getRoleType())) {
            booleanBuilder.and(QUserEntity.userEntity.roleType.eq(query.getRoleType()));
        }

        if(Objects.nonNull(query.getEnabled())) {
            booleanBuilder.and(QUserEntity.userEntity.enabled.eq(query.getEnabled()));
        }

        return booleanBuilder;
    }
}