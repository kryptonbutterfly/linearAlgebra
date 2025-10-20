package kryptonbutterfly.math.vector._double;

import static kryptonbutterfly.math.AlgebraSettings.*;

import java.util.Arrays;

import kryptonbutterfly.math.vector.IVector;

public interface IVecD<Vec extends IVecD<Vec>> extends IVector<Vec>
{
	/**
	 * Calculates the dot product. <br/>
	 * a·b = ‖a‖ ‖b‖ cos(θ)
	 * 
	 * @param other
	 *            The second vector to form the dot product with
	 * @return The dot product.
	 */
	public default double dotProduct(Vec other)
	{
		validateDimensionalityMatch(other);
		double sum = 0;
		for (int i = 0; i < dimensions(); i++)
			sum += get(i) * other.get(i);
		return sum;
	}
	
	/**
	 * @return the length of {@code this} vector squared.
	 */
	@SuppressWarnings("unchecked")
	public default double lengthSQ()
	{
		return this.dotProduct((Vec) this);
	}
	
	@Override
	public default double length()
	{
		return Math.sqrt(lengthSQ());
	}
	
	@Override
	public default Vec projectOn(Vec canvas)
	{
		final var cn = canvas.norm();
		return cn.scale(dotProduct(cn));
	}
	
	/**
	 * @return a double[] representing {@code this} vector.
	 */
	public double[] toArray();
	
	/**
	 * @param dimension
	 * @return This vectors magnitude in the provided direction.
	 */
	public double get(int dimension);
	
	static <Vec extends IVecD<Vec>> boolean equals(Vec left, Object right)
	{
		if (left == right)
			return true;
		if (!(right instanceof IVecD r))
			return false;
		if (left.dimensions() != r.dimensions())
			return false;
		for (int i = 0; i < left.dimensions(); i++)
		{
			final double diff = left.get(i) - r.get(i);
			if (Math.abs(diff) > settings.DOUBLE_EPSILON)
				return false;
		}
		return true;
	}
	
	static <Vec extends IVecD<Vec>> int hashCode(Vec vec)
	{
		return Arrays.hashCode(vec.toArray());
	}
	
	/**
	 * @param <Vec>
	 * @param vec
	 * @return A string representation of {@code vec}.
	 */
	static <Vec extends IVecD<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder()
			.append(vec.getClass().getSimpleName())
			.append('(');
		
		for (int i = 0; i < vec.dimensions(); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(settings.FP_STRINGIFY_TEMPLATE.formatted(vec.get(i)));
		}
		
		sb.append(')');
		return sb.toString();
	}
}