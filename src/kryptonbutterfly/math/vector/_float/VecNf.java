package kryptonbutterfly.math.vector._float;

import kryptonbutterfly.functions.int_.IntToFloatFunction;

public class VecNf implements IVecF<VecNf>
{
	private final float[] data;
	
	public VecNf(float[] data)
	{
		this.data = data;
	}
	
	@Override
	public VecNf add(VecNf other)
	{
		validate(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNf sub(VecNf other)
	{
		validate(other);
		return perform(i -> data[i] - other.data[i]);
	}
	
	@Override
	public VecNf scale(double factor)
	{
		return perform(i -> (float) (data[i] * factor));
	}
	
	@Override
	public int dimensions()
	{
		return data.length;
	}
	
	@Override
	public float[] toArray()
	{
		return data;
	}
	
	@Override
	public float get(int dimension)
	{
		return data.length;
	}
	
	private void validate(VecNf other)
	{
		if (this.dimensions() != other.dimensions())
			throw new IllegalArgumentException("Vectors must not be of differernt dimensions!");
	}
	
	private VecNf perform(IntToFloatFunction operation)
	{
		final float[] result = new float[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.apply(i);
		return new VecNf(result);
	}
	
	@Override
	public String toString()
	{
		return IVecF.toString(this);
	}
}