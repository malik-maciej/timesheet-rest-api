package com.malik.restapi.specification;

import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.model.User;
import com.malik.restapi.model.User_;
import com.malik.restapi.util.UserType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public class UserSpecification implements Specification<User> {

    private static final String ANY_PATTERN = "%";

    private final UserFilterForm filter;

    public UserSpecification(final UserFilterForm filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (nonNull(filter.getLogin())) {
            predicates.add(builder.like(root.get(User_.login), ANY_PATTERN + filter.getLogin() + ANY_PATTERN));
        }

        if (nonNull(filter.getName())) {
            predicates.add(builder.like(root.get(User_.name), ANY_PATTERN + filter.getName() + ANY_PATTERN));
        }

        if (nonNull(filter.getSurname())) {
            predicates.add(builder.like(root.get(User_.surname), ANY_PATTERN + filter.getSurname() + ANY_PATTERN));
        }

        if (nonNull(filter.getType())) {
            Stream.of(UserType.values()).forEach(userType -> {
                if (userType.toString().equals(filter.getType().toUpperCase())) {
                    predicates.add(builder.equal(root.get(User_.type), userType));
                }
            });
        }

        if (nonNull(filter.getMinSalary())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(User_.salaryPerHour), filter.getMinSalary()));
        }

        if (nonNull(filter.getMaxSalary())) {
            predicates.add(builder.lessThanOrEqualTo(root.get(User_.salaryPerHour), filter.getMaxSalary()));
        }

        return query.where(builder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).orderBy(builder.desc(root.get(User_.login))).getRestriction();
    }
}