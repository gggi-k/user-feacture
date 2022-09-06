package kr.submit.userfeature.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.submit.userfeature.core.jpa.template.PagingRepositoryTemplate;
import kr.submit.userfeature.user.dto.UserQuery;
import kr.submit.userfeature.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static kr.submit.userfeature.user.domain.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements PagingRepositoryTemplate<UserResponse, UserQuery> {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Long findCountByQuery(UserQuery query) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(userEntity)
                .where(whereClause(query))
                .fetchOne();
    }

    @Override
    public List<UserResponse> findAllByQueryWithPaging(UserQuery query) {
        return jpaQueryFactory.select(Projections.bean(
                    UserResponse.class,
                    userEntity.userId,
                    userEntity.email,
                    userEntity.phoneNumber,
                    userEntity.nickname,
                    userEntity.name,
                    userEntity.enabled,
                    userEntity.roleType,
                    userEntity.createdAt,
                    userEntity.updatedAt
                ))
                .from(userEntity)
                .where(whereClause(query))
                .offset(query.getPageable().getOffset())
                .limit(query.getPageable().getPageSize())
                .fetch();
    }

    private BooleanBuilder whereClause(UserQuery query) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(query.getEmail())) {
            booleanBuilder.and(userEntity.email.contains(query.getEmail()));
        }

        if(StringUtils.hasText(query.getPhoneNumber())) {
            booleanBuilder.and(userEntity.phoneNumber.contains(query.getPhoneNumber()));
        }

        if(StringUtils.hasText(query.getNickname())) {
            booleanBuilder.and(userEntity.nickname.contains(query.getNickname()));
        }

        if(StringUtils.hasText(query.getName())) {
            booleanBuilder.and(userEntity.name.contains(query.getName()));
        }

        if(Objects.nonNull(query.getRoleType())) {
            booleanBuilder.and(userEntity.roleType.eq(query.getRoleType()));
        }

        if(Objects.nonNull(query.getEnabled())) {
            booleanBuilder.and(userEntity.enabled.eq(query.getEnabled()));
        }

        return booleanBuilder;
    }
}