package com.malik.restapi.specification;

import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.model.ProjectView;
import com.malik.restapi.model.ProjectView_;
import com.malik.restapi.model.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class ProjectSpecification implements Specification<ProjectView> {

    private static final String ANY_PATTERN = "%";

    private final ProjectFilterForm filter;

    public ProjectSpecification(final ProjectFilterForm filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(final Root<ProjectView> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (nonNull(filter.getName())) {
            predicates.add(builder.like(root.get(ProjectView_.name), ANY_PATTERN + filter.getName() + ANY_PATTERN));
        }

        if (nonNull(filter.getStartDate())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ProjectView_.startDate), filter.getStartDate()));
        }

        if (nonNull(filter.getEndDate())) {
            predicates.add(builder.lessThanOrEqualTo(root.get(ProjectView_.endDate), filter.getEndDate()));
        }

        if (nonNull(filter.getUsers())) {
            predicates.add(builder.and(getUsersPredicates(root, builder)));
        }

        if (nonNull(filter.getBudgetExceeded())) {
            predicates.add(builder.equal(root.get(ProjectView_.budgetExceeded), filter.getBudgetExceeded()));
        }

        return query.where(builder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).orderBy(builder.desc(root.get(ProjectView_.name))).getRestriction();
    }

    private Predicate[] getUsersPredicates(final Root<ProjectView> root, final CriteriaBuilder builder) {
        final Predicate[] usersPredicates = new Predicate[filter.getUsers().size() + 1];
        IntStream.range(0, filter.getUsers().size()).forEach(i ->
                usersPredicates[i] = builder.equal(root.join(ProjectView_.users).get(User_.login), filter.getUsers().get(i)));

        usersPredicates[usersPredicates.length - 1] =
                builder.like(root.join(ProjectView_.users).get(User_.login), ANY_PATTERN);

        return usersPredicates;
    }
}