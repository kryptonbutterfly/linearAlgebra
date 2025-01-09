package kryptonbutterfly.math.vector._double;

import java.util.function.IntToDoubleFunction;

public class VecNd implements IVecD<VecNd>
{
	/**
	 * The backing array of this vector.
	 */
	private final double[] data;
	
	/**
	 * Creates a new Vector using the supplied values.
	 * 
	 * @param data
	 *            The values to construct the vector from.
	 * 			
	 */
	public VecNd(double... data)
	{
		this.data = data;
	}
	
	@Override
	public VecNd add(VecNd other)
	{
		validateDimensionalityMatch(other);
		return perform(i -> data[i] + other.data[i]);
	}
	
	@Override
	public VecNd sub(VecNd other)
	{
		validateDimensionalityMatch(other);
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
	
	/**
	 * Performs the supplied {@code operation} for every dimension of {@code this}
	 * vector and collects the results in a new instance.
	 * 
	 * @param operation
	 *            The operation to be performed upon every dimension of {@code this}
	 *            vector.
	 * @return A new Vector containing the result of the performed operations.
	 */
	private VecNd perform(IntToDoubleFunction operation)
	{
		final double[] result = new double[dimensions()];
		for (int i = 0; i < dimensions(); i++)
			result[i] = operation.applyAsDouble(i);
		return new VecNd(result);
	}
	
	@Override
	/**
	 * @return A string representation of {@code this} vector.
	 */
	public String toString()
	{
		return IVecD.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecD.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecD.hashCode(this);
	}
}