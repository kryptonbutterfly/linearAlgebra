package kryptonbutterfly.math.vector._double;

import kryptonbutterfly.math.vector.IVector;

public interface IVecD<Vec extends IVecD<Vec>> extends IVector<Vec>
{
	public default double dotProduct(Vec other)
	{
		final var	lData	= toArray();
		final var	rData	= other.toArray();
		
		double sum = 0;
		for (int i = 0; i < lData.length; i++)
			sum += lData[i] * rData[i];
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public default double lengthSQ()
	{
		return this.dotProduct((Vec) this);
	}
	
	@Override
	public default double length()
	{
		return Math.sqrt(lengthSQ());
	}
	
	public default Vec projectOn(Vec canvas)
	{
		return canvas.norm().scale(dotProduct(canvas));
	}
	
	public double[] toArray();
	
	public double get(int dimension);
	
	static <Vec extends IVecD<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder();
		sb.append(vec.getClass().getSimpleName());
		sb.append('(');
		
		boolean isFirst = true;
		for (double e : vec.toArray())
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