package kryptonbutterfly.math.vector._float;

import java.util.Arrays;

import kryptonbutterfly.math.vector.IVector;

public interface IVecF<Vec extends IVecF<Vec>> extends IVector<Vec>
{
	/**
	 * Calculates the dot product. <br/>
	 * a·b = ‖a‖ ‖b‖ cos(θ)
	 * 
	 * @param other
	 *            The second vector to form the dot product with
	 * @return The dot product.
	 */
	public default float dotProduct(Vec other)
	{
		validateDimensionalityMatch(other);
		float sum = 0;
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
		return dotProduct((Vec) this);
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
	 * @return a float[] representing {@code this} vector.
	 */
	public float[] toArray();
	
	/**
	 * @param dimension
	 * @return This vectors magnitude in the provided direction.
	 */
	public float get(int dimension);
	
	static <Vec extends IVecF<Vec>> boolean equals(Vec left, Object right)
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
	
	static <Vec extends IVecF<Vec>> int hashCode(Vec vec)
	{
		return Arrays.hashCode(vec.toArray());
	}
	
	/**
	 * @param <Vec>
	 * @param vec
	 * @return A string representation of {@code vec}.
	 */
	static <Vec extends IVecF<Vec>> String toString(Vec vec)
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