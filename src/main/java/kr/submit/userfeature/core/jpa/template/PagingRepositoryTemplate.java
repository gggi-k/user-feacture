package kr.submit.userfeature.core.jpa.template;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PagingRepositoryTemplate<DTO, QUERY> {

    Long findCountByQuery(final QUERY query);

    List<DTO> findAllByQueryWithPaging(final QUERY query);
}