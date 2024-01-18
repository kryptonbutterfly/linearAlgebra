package kryptonbutterfly.math.vector._long;

import java.util.function.IntToLongFunction;

public class VecNl implements IVecL<VecNl>
{
	private final long[] data;
	
	public VecNl(long[] data)
	{
		this.data = data;
	}
	
	@Override
	public VecNl add(VecNl other)
	{
		validate(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNl sub(VecNl other)
	{
		validate(other);
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
	
	private void validate(VecNl other)
	{
		if (this.dimensions() != other.dimensions())
			throw new IllegalArgumentException("Vectors must not be of differernt dimensions!");
	}
	
	private VecNl perform(IntToLongFunction operation)
	{
		final long[] result = new long[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsLong(i);
		return new VecNl(result);
	}
	
	@Override
	public String toString()
	{
		return IVecL.toString(this);
	}
}