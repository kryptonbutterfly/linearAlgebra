package kryptonbutterfly.math.vector._int;

import java.util.function.IntUnaryOperator;

public class VecNi implements IVecI<VecNi>
{
	private final int[] data;
	
	public VecNi(int[] data)
	{
		this.data = data;
	}
	
	@Override
	public VecNi add(VecNi other)
	{
		validate(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNi sub(VecNi other)
	{
		validate(other);
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
	
	private void validate(VecNi other)
	{
		if (this.dimensions() != other.dimensions())
			throw new IllegalArgumentException("Vectors must not be of different dimensions!");
	}
	
	private VecNi perform(IntUnaryOperator operation)
	{
		final int[] result = new int[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsInt(i);
		return new VecNi(result);
	}
	
	@Override
	public String toString()
	{
		return IVecI.toString(this);
	}
}