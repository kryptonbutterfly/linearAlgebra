package kryptonbutterfly.math.vector._int;

import java.util.function.IntUnaryOperator;

public class VecNi implements IVecI<VecNi>
{
	/**
	 * The backing array of this vector.
	 */
	private final int[] data;
	
	/**
	 * Creates a new Vector using the supplied values.
	 * 
	 * @param data
	 *            The values to construct the vector from.
	 */
	public VecNi(int... data)
	{
		this.data = data;
	}
	
	@Override
	public VecNi add(VecNi other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNi sub(VecNi other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] - other.data[i]);
	}
	
	@Override
	public VecNi scale(double factor)
	{
		return perform(i -> (int) (data[i] * factor));
	}
	
	@Override
	public int dimensions()
	{
		return data.length;
	}
	
	@Override
	public int[] toArray()
	{
		return data;
	}
	
	@Override
	public int get(int dimension)
	{
		return data[dimension];
	}
	
	/**
	 * Performs the supplied {@code operation} for every dimension of {@code this}
	 * vector and collects the results in a new instance.
	 * 
	 * @param operation
	 *            The operation to be performed upon every dimension of {@code this}
	 *            vector.
	 * @return A new Vector containing the result of the performed operations.
	 */
	private VecNi perform(IntUnaryOperator operation)
	{
		final int[] result = new int[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsInt(i);
		return new VecNi(result);
	}
	
	@Override
	/**
	 * @return A string representation of {@code this} vector.
	 */
	public String toString()
	{
		return IVecI.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecI.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecI.hashCode(this);
	}
}