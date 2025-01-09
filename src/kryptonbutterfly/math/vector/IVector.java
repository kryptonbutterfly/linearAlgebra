package kryptonbutterfly.math.vector;

import kryptonbutterfly.math.DimensionalityMismatchException;

public interface IVector<Vec extends IVector<Vec>>
{
	/**
	 * @param other
	 *            The vector to sum with {@code this} vector.
	 * @return The sum of {@code this} and {@code other}.
	 */
	public Vec add(Vec other);
	
	/**
	 * @param other
	 *            The vector to subtract from {@code this} vector.
	 * @return The difference between {@code this} vector and {@code other}.
	 */
	public Vec sub(Vec other);
	
	/**
	 * @param factor
	 *            The factor to scale {@code this} vector by.
	 * @return A vector with the same direction as {@code this} vector but
	 *         {@code factor} × longer.
	 */
	public Vec scale(double factor);
	
	/**
	 * @return A vector with the same direction as {@code this} vector but length 1.
	 * 
	 * @throws java.lang.ArithmeticException
	 *             if {@code this} vector is of length 0.
	 */
	public default Vec norm()
	{
		return scale(1 / length());
	}
	
	/**
	 * @return The length of {@code this} vector.
	 */
	public double length();
	
	/**
	 * @param canvas
	 *            The Vector to project {@code this} vector onto.
	 * @return A new Vector that is a projection of {@code this} vector onto the
	 *         canvas vector.
	 */
	public Vec projectOn(Vec canvas);
	
	/**
	 * @return The dimensionality of {@code this} Vector.
	 */
	public int dimensions();
	
	/**
	 * This method accepts a provided dimension and generates an
	 * IndexOutOfBoundsException with the appropriate message.
	 * 
	 * @param dimension
	 *            The dimension that was attempted to be accessed.
	 * @return A new IndexOutOfBoundsException describing what happened.
	 */
	public default IndexOutOfBoundsException indexOutOfBounds(int dimension)
	{
		return new IndexOutOfBoundsException(
			"Unable to access dimension %d. Only %d dimensions are available.".formatted(dimension, dimensions()));
	}
	
	/**
	 * Validates that {@code other} has the same dimensionality as {@code this}.
	 * 
	 * @param other
	 */
	public default void validateDimensionalityMatch(Vec other)
	{
		if (dimensions() != other.dimensions())
			throw new DimensionalityMismatchException(dimensions(), other.dimensions());
	}
	
	boolean equals(Object other);
}