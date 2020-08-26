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

public class ProjectSpecification implements Specification<ProjectView> {

    private static final String ANY_PATTERN = "%";

    private final ProjectFilterForm filter;

    public ProjectSpecification(ProjectFilterForm filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<ProjectView> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null) {
            predicates.add(builder.like(root.get(ProjectView_.name), ANY_PATTERN + filter.getName() + ANY_PATTERN));
        }

        if (filter.getStartDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ProjectView_.startDate), filter.getStartDate()));
        }

        if (filter.getEndDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(ProjectView_.endDate), filter.getEndDate()));
        }

        if (filter.getUsers() != null) {
            predicates.add(builder.and(getUsersPredicates(root, builder)));
        }

        if (filter.getBudgetExceeded() != null) {
            predicates.add(builder.equal(root.get(ProjectView_.budgetExceeded), filter.getBudgetExceeded()));
        }

        return query.where(builder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).orderBy(builder.desc(root.get(ProjectView_.name))).getRestriction();
    }

    private Predicate[] getUsersPredicates(Root<ProjectView> root, CriteriaBuilder builder) {
        Predicate[] usersPredicates = new Predicate[filter.getUsers().size() + 1];
        for (int i = 0; i < filter.getUsers().size(); i++) {
            usersPredicates[i] = builder.equal(root.join(ProjectView_.users).get(User_.login), filter.getUsers().get(i));
        }

        usersPredicates[usersPredicates.length - 1] = builder.like(
                root.join(ProjectView_.users).get(User_.login), ANY_PATTERN);

        return usersPredicates;
    }
}