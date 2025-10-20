package kryptonbutterfly.math.geometry;

import kryptonbutterfly.math.DimensionalityMismatchException;
import kryptonbutterfly.math.vector.IVector;

/**
 * A line through n dimensional space.
 * 
 * @author kryptonbutterfly
 *
 * @param <Vec>
 */
public class Line<Vec extends IVector<Vec>>
{
	private final Vec support, direction;
	
	/**
	 * This constructor expects two vectors of equal type and dimensionality.
	 * 
	 * @param support
	 *            The vector representation of an arbitrary point on the line to be
	 *            constructed.
	 * @param direction
	 *            A vector parallel to the line to be constructed.
	 * 			
	 * @throws IllegalArgumentException
	 *             if the argument types don't match.
	 * @throws DimensionalityMismatchException
	 *             if the dimensions don't match.
	 */
	public Line(Vec support, Vec direction)
	{
		assertEqualTypes(support, direction);
		support.validateDimensionalityMatch(direction);
		this.support	= support;
		this.direction	= direction;
	}
	
	/**
	 * @return A vector representation of an arbitrary point on {@code this} line.
	 */
	public Vec support()
	{
		return this.support;
	}
	
	/**
	 * @return A vector parallel to {@code this} line.
	 */
	public Vec direction()
	{
		return this.direction;
	}
	
	/**
	 * @param target
	 *            A vector representation of an arbitrary point in space.
	 * @return The distance of the provided point to this line.
	 */
	public double distance(Vec target)
	{
		final var rTarget = target.sub(support);
		return rTarget.sub(rTarget.projectOn(this.direction)).length();
	}
	
	private static <Vec extends IVector<Vec>> void assertEqualTypes(Vec support, Vec direction)
	{
		final var	sClass	= support.getClass();
		final var	dClass	= direction.getClass();
		if (sClass != dClass)
			throw new IllegalArgumentException(
				"Support is of type %s, while direction is of type %s. Both arguments must be of the same type."
					.formatted(sClass, dClass));
	}
}
