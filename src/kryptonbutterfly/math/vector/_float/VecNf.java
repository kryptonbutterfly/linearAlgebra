package kryptonbutterfly.math.vector._float;

import kryptonbutterfly.functions.int_.IntToFloatFunction;

public class VecNf implements IVecF<VecNf>
{
	/**
	 * The backing array of this vector.
	 */
	private final float[] data;
	
	/**
	 * Creates a new Vector using the supplied values.
	 * 
	 * @param data
	 *            The values to construct the vector from.
	 */
	public VecNf(float... data)
	{
		this.data = data;
	}
	
	@Override
	public VecNf add(VecNf other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNf sub(VecNf other)
	{
		validateDimensionalityMatch(other);
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
		return data[dimension];
	}
	
	/**
	 * Performs the supplied {@code operation} for every dimension of {@code this}
	 * vector and collects the results in a new instance.
	 * 
	 * @param operation
	 *            The operation to be performed upon every dimension of {@code this}
	 *            vector.
	 * @return A new vector containing the result of the performed operations.
	 */
	private VecNf perform(IntToFloatFunction operation)
	{
		final float[] result = new float[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.apply(i);
		return new VecNf(result);
	}
	
	@Override
	/**
	 * @return A string representation of {@code this} vector.
	 */
	public String toString()
	{
		return IVecF.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecF.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecF.hashCode(this);
	}
}