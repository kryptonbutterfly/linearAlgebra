package kryptonbutterfly.math.vector._double;

import java.util.function.IntToDoubleFunction;

public class VecNd implements IVecD<VecNd>
{
	private final double[] data;
	
	public VecNd(double[] data)
	{
		this.data = data;
	}
	
	@Override
	public VecNd add(VecNd other)
	{
		validate(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNd sub(VecNd other)
	{
		validate(other);
		return perform(i -> data[i] - other.data[i]);
	}
	
	@Override
	public VecNd scale(double factor)
	{
		return perform(i -> data[i] * factor);
	}
	
	@Override
	public int dimensions()
	{
		return data.length;
	}
	
	@Override
	public double[] toArray()
	{
		return data;
	}
	
	@Override
	public double get(int dimension)
	{
		return data[dimension];
	}
	
	private void validate(VecNd other)
	{
		if (this.dimensions() != other.dimensions())
			throw new IllegalArgumentException("Vectors must not be of differernt dimensions!");
	}
	
	private VecNd perform(IntToDoubleFunction operation)
	{
		final double[] result = new double[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsDouble(i);
		return new VecNd(result);
	}
	
	@Override
	public String toString()
	{
		return IVecD.toString(this);
	}
}