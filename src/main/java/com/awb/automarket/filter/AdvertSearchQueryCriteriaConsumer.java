package com.awb.automarket.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;


@Data
public class AdvertSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {
    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder
                    .greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                    r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (r.get(param.getKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(
                        r.get(param.getKey()), "%" + param.getValue() + "%"));
            } else {
                predicate = builder.and(predicate, builder.equal(
                        r.get(param.getKey()), param.getValue()));
            }
        }
    }

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Root getR() {
		return r;
	}

	public void setR(Root r) {
		this.r = r;
	}

	public AdvertSearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root r) {
		super();
		this.predicate = predicate;
		this.builder = builder;
		this.r = r;
	}

}
