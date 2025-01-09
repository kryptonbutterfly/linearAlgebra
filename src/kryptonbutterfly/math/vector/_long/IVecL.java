package kryptonbutterfly.math.vector._long;

import java.util.Arrays;

import kryptonbutterfly.math.vector.IVector;

public interface IVecL<Vec extends IVecL<Vec>> extends IVector<Vec>
{
	/**
	 * Calculates the dot product. <br/>
	 * a·b = ‖a‖ ‖b‖ cos(θ)
	 * 
	 * @param other
	 *            The second vector to form the dot product with
	 * @return The dot product.
	 */
	public default long dotProduct(Vec other)
	{
		validateDimensionalityMatch(other);
		long sum = 0;
		for (int i = 0; i < dimensions(); i++)
			sum += get(i) * other.get(i);
		return sum;
	}
	
	/**
	 * @return the length of {@code this} vector squared.
	 */
	@SuppressWarnings("unchecked")
	public default long lengthSQ()
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
	 * @return a long[] representing {@code this} vector.
	 */
	public long[] toArray();
	
	/**
	 * @param dimension
	 * @return This vectors magnitude in the provided direction.
	 */
	public long get(int dimension);
	
	static <Vec extends IVecL<Vec>> boolean equals(Vec left, Object right)
	{
		if (left == right)
			return true;
		if (right.getClass() != left.getClass())
			return false;
		@SuppressWarnings("unchecked")
		final var vec = (Vec) right;
		if (left.dimensions() != vec.dimensions())
			return false;
		for (int i = 0; i < left.dimensions(); i++)
			if (left.get(i) != vec.get(i))
				return false;
		return true;
	}
	
	static <Vec extends IVecL<Vec>> int hashCode(Vec vec)
	{
		return Arrays.hashCode(vec.toArray());
	}
	
	/**
	 * @param <Vec>
	 * @param vec
	 * @return A string representation of {@code vec}
	 */
	static <Vec extends IVecL<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder()
			.append(vec.getClass().getSimpleName())
			.append('(');
		
		for (int i = 0; i < vec.dimensions(); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(vec.get(i));
		}
		
		sb.append(')');
		
		return sb.toString();
	}
}