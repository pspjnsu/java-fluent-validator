package br.com.fluentvalidator.rule;

import java.util.Objects;
import java.util.function.Predicate;

import br.com.fluentvalidator.ValidationContext;
import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.exception.ValidationException;

abstract class ValidationRule<T, P> implements Validation<T, P> {

	private final Predicate<P> when;
	
	private Predicate<P> must = m -> true;

	private String message;
	
	private String code;

	private String fieldName;

	private boolean critical;
	
	private Class<? extends ValidationException> criticalException;

	private Validator<T> validator;
	
	protected ValidationRule(final Predicate<P> when) {
		this.when = when;
	}

	public Predicate<P> getWhen() {
		return this.when;
	}

	public Predicate<P> getMust() {
		return this.must;
	}

	public String getMessage() {
		return this.message;
	}
	
	public String getCode() {
		return code;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public boolean isCritical() {
		return this.critical;
	}

	public Validator<T> getValidator() {
		return this.validator;
	}

	@Override
	public void must(final Predicate<P> must) {
		this.must = must;
	}

	@Override
	public void withFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void withMessage(final String message) {
		this.message = message;
	}
	
	@Override
	public void withCode(final String code) {
		this.code = code;
	}

	@Override
	public void withValidator(final Validator<T> validator) {
		this.validator = validator;
	}

	@Override
	public void critical() {
		this.critical = true;
	}
	
	public void critical(final Class<? extends ValidationException> clazz) {
		this.criticalException = clazz;
	}

	/*
	 * +----------+-----------+--------+
	 * | critical | composite | result |
	 * +----------+-----------+--------|
	 * | true     | true      | true   |
	 * | true     | false     | false  |
	 * | false    | true      | true   |
	 * | false    | false     | true   |
	 * +----------+-----------+--------+
	 */
	@Override
	public boolean apply(final P instance) {
		
		if (!this.getWhen().test(instance)) return true;
		
		boolean apply = this.getMust().test(instance);
		
		if (Boolean.FALSE.equals(apply)) {
			ValidationContext.get().addError(this.getFieldName(), this.getMessage(), this.getCode(), instance);
		}
				
		if (Objects.nonNull(instance) && Objects.nonNull(this.getValidator())) {
			apply = accept(instance);
		}
		
		if (Objects.nonNull(criticalException) && Boolean.FALSE.equals(apply)) {
			throw ValidationException.create(criticalException);
		}
		
		return !(Boolean.TRUE.equals(this.isCritical()) && Boolean.FALSE.equals(apply));
	}
	
	abstract boolean accept(final P instance);

}
