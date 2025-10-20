package kryptonbutterfly.math.vector._int;

import java.util.Arrays;

import kryptonbutterfly.math.vector.IVector;

public interface IVecI<Vec extends IVecI<Vec>> extends IVector<Vec>
{
	/**
	 * Calculates the dot product. <br/>
	 * a·b = ‖a‖ ‖b‖ cos(θ)
	 * 
	 * @param other
	 *            The second vector to form the dot product with
	 * @return The dot product.
	 */
	public default int dotProduct(Vec other)
	{
		validateDimensionalityMatch(other);
		int sum = 0;
		for (int i = 0; i < dimensions(); i++)
			sum += get(i) * other.get(i);
		return sum;
	}
	
	/**
	 * @return the length of {@code this} vector squared.
	 */
	@SuppressWarnings("unchecked")
	public default int lengthSQ()
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
		return cn.scale(this.dotProduct(cn));
	}
	
	/**
	 * @return an int[] representing {@code this} vector.
	 */
	public int[] toArray();
	
	/**
	 * @param dimension
	 * @return This vectors magnitude in the provided direction.
	 */
	public int get(int dimension);
	
	static <Vec extends IVecI<Vec>> boolean equals(Vec left, Object right)
	{
		if (left == right)
			return true;
		if (!(right instanceof IVecI r))
			return false;
		if (left.dimensions() != r.dimensions())
			return false;
		for (int i = 0; i < left.dimensions(); i++)
			if (left.get(i) != r.get(i))
				return false;
		return true;
	}
	
	static <Vec extends IVecI<Vec>> int hashCode(Vec vec)
	{
		return Arrays.hashCode(vec.toArray());
	}
	
	/**
	 * @param <Vec>
	 * @param vec
	 * @return A string representation of {@code vec}.
	 */
	static <Vec extends IVecI<Vec>> String toString(Vec vec)
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