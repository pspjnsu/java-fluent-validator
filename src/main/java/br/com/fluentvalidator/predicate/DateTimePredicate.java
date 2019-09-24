package br.com.fluentvalidator.predicate;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Predicate;

public final class DateTimePredicate {

	private DateTimePredicate() {
		super();
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeEqualTo(final Function<T, String> source, final Function<T, String> target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeEqualTo(target.apply(obj), pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeGreaterThan(final Function<T, String> source, final Function<T, String> target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeGreaterThan(target.apply(obj), pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeLessThan(final Function<T, String> source, final Function<T, String> target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeLessThan(target.apply(obj), pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeGreaterThanOrEqual(final Function<T, String> source, final Function<T, String> target, final String pattern) {
		return PredicateBuilder.<T>from(dateTimeGreaterThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeLessThanOrEqual(final Function<T, String> source, final Function<T, String> target, final String pattern) {
		return PredicateBuilder.<T>from(dateTimeLessThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeEqualTo(final Function<T, String> source, final String target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeEqualTo(target, pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeGreaterThan(final Function<T, String> source, final String target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeGreaterThan(target, pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeLessThan(final Function<T, String> source, final String target, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeLessThan(target, pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeGreaterThanOrEqual(final Function<T, String> source, final String target, final String pattern) {
		return PredicateBuilder.<T>from(dateTimeGreaterThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeLessThanOrEqual(final Function<T, String> source, final String target, final String pattern) {
		return PredicateBuilder.<T>from(dateTimeLessThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
	}

	/**
	 *
	 * @param <T>
	 * @param source
	 * @param dateStringMin
	 * @param dateStringMax
	 * @param pattern
	 * @return
	 */
	public static <T> Predicate<T> dateTimeBetween(final Function<T, String> source, final String dateStringMin, final String dateStringMax, final String pattern) {
		return PredicateBuilder.<T>from(not(nullValue())).and(obj -> dateTimeBetween(dateStringMin, dateStringMax, pattern).test(source.apply(obj)));
	}

	/**
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeEqualTo(final String dateString, final String pattern) {
		return PredicateBuilder.<String>from(not(nullValue())).and(dateTimeEqualTo -> not(stringEmptyOrNull()).test(dateString))
		        .and(dateTimeEqualTo -> not(stringEmptyOrNull()).test(pattern)).and(dateTimeEqualTo -> {
			        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
			        final LocalDateTime dateTimeTest = LocalDateTime.parse(dateTimeEqualTo, dateFormat);
			        final LocalDateTime dateTime = LocalDateTime.parse(dateString, dateFormat);
			        return dateTimeTest.isEqual(dateTime);
		        });
	}

	/**
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeGreaterThan(final String dateString, final String pattern) {
		return PredicateBuilder.<String>from(not(nullValue())).and(dateTimeGreaterThan -> not(stringEmptyOrNull()).test(dateString))
		        .and(dateTimeGreaterThan -> not(stringEmptyOrNull()).test(pattern)).and(dateTimeGreaterThan -> {
			        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
			        final LocalDateTime dateTimeTest = LocalDateTime.parse(dateTimeGreaterThan, dateFormat);
			        final LocalDateTime dateTime = LocalDateTime.parse(dateString, dateFormat);
			        return dateTimeTest.isAfter(dateTime);
		        });
	}

	/**
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeLessThan(final String dateString, final String pattern) {
		return PredicateBuilder.<String>from(not(nullValue())).and(dateTimeLessThan -> not(stringEmptyOrNull()).test(dateString))
		        .and(dateTimeLessThan -> not(stringEmptyOrNull()).test(pattern)).and(dateTimeLessThan -> {
			        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
			        final LocalDateTime dateTest = LocalDateTime.parse(dateTimeLessThan, dateFormat);
			        final LocalDateTime date = LocalDateTime.parse(dateString, dateFormat);
			        return dateTest.isBefore(date);
		        });
	}

	/**
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeGreaterThanOrEqual(final String dateString, final String pattern) {
		return PredicateBuilder.<String>from(dateTimeGreaterThan(dateString, pattern).or(dateTimeEqualTo(dateString, pattern)));
	}

	/**
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeLessThanOrEqual(final String dateString, final String pattern) {
		return PredicateBuilder.<String>from(dateTimeLessThan(dateString, pattern).or(dateTimeEqualTo(dateString, pattern)));
	}

	/**
	 *
	 * @param dateStringMin
	 * @param dateStringMax
	 * @param pattern
	 * @return
	 */
	public static Predicate<String> dateTimeBetween(final String dateStringMin, final String dateStringMax, final String pattern) {
		return PredicateBuilder.<String>from(dateTimeLessThanOrEqual(dateStringMax, pattern).and(dateTimeGreaterThanOrEqual(dateStringMin, pattern)));
	}

}