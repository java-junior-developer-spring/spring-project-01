package com.itmo.springproject01.info.queries;

import com.itmo.springproject01.entity.Picture;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.domain.Specification;

public class BoxSpecifications {
    public static Specification<Box> spec01(int w, int h, int l) {
        // jakarta.persistence.criteria
        // Predicate toPredicate(Root<T> root,
        // CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
        // WHERE width = :w  AND ( height < :h OR length < :l)
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(criteriaBuilder.equal(root.get("width"), w),
                    criteriaBuilder.or(
                            criteriaBuilder.lessThan(root.get("height"), h),
                            criteriaBuilder.lessThan(root.get("length"), l)
                    )
            );
        });
    }

    // TODO спецификации с join
    // Join<Picture, Genre> join01 = root.join("genre", JoinType.LEFT);
    // Join<Genre, Type> join = join01.join("genre", JoinType.LEFT);
}
