package kryptonbutterfly.math.vector._long;

import kryptonbutterfly.math.vector.IVector;

public interface IVecL<Vec extends IVecL<Vec>> extends IVector<Vec>
{
	public default long dotProduct(Vec other)
	{
		final var	lData	= toArray();
		final var	rData	= other.toArray();
		
		long sum = 0;
		for (int i = 0; i < lData.length; i++)
			sum += lData[i] * rData[i];
		
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public default long lengthSQ()
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
		return canvas.norm().scale(dotProduct(canvas));
	}
	
	public long[] toArray();
	
	public long get(int dimension);
	
	static <Vec extends IVecL<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder();
		sb.append(vec.getClass().getSimpleName());
		sb.append('(');
		
		boolean isFirst = true;
		for (long e : vec.toArray())
		{
			if (isFirst)
				isFirst = false;
			else
				sb.append(", ");
			sb.append(e);
		}
		sb.append(')');
		
		return sb.toString();
	}
}