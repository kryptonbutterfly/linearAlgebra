package kryptonbutterfly.math.vector._long;

import java.util.function.IntToLongFunction;

public class VecNl implements IVecL<VecNl>
{
	/**
	 * The backing array of this vector.
	 */
	private final long[] data;
	
	/**
	 * Creates a new Vector using the supplied values.
	 * 
	 * @param data
	 *            The values to construct the vector from.
	 */
	public VecNl(long... data)
	{
		this.data = data;
	}
	
	@Override
	public VecNl add(VecNl other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNl sub(VecNl other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] - other.data[i]);
	}
	
	@Override
	public VecNl scale(double factor)
	{
		return perform(i -> (long) (data[i] * factor));
	}
	
	@Override
	public int dimensions()
	{
		return data.length;
	}
	
	@Override
	public long[] toArray()
	{
		return data;
	}
	
	@Override
	public long get(int dimension)
	{
		return data[dimension];
	}
	
	/**
	 * Performs the supplied {@code operation} for every dimension of {@code this}
	 * vector and collects the result in a new instance.
	 * 
	 * @param operation
	 *            The operation to be performed upon every dimension of {@code this}
	 *            vector.
	 * @return A new Vector containing the result of the performed operations.
	 */
	private VecNl perform(IntToLongFunction operation)
	{
		final long[] result = new long[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsLong(i);
		return new VecNl(result);
	}
	
	@Override
	/**
	 * @return A string representation of {@code this} vector.
	 */
	public String toString()
	{
		return IVecL.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecL.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecL.hashCode(this);
	}
}