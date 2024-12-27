package kryptonbutterfly.math.vector._int;

import kryptonbutterfly.math.vector.IVector;

public interface IVecI<Vec extends IVecI<Vec>> extends IVector<Vec>
{
	public default int dotProduct(Vec other)
	{
		final var	lData	= toArray();
		final var	rData	= other.toArray();
		
		int sum = 0;
		for (int i = 0; i < lData.length; i++)
			sum += lData[i] * rData[i];
		
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public default int lengthSQ()
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
		return canvas.norm().scale(this.dotProduct(canvas));
	}
	
	public int[] toArray();
	
	public int get(int dimension);
	
	static <Vec extends IVecI<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder();
		sb.append(vec.getClass().getSimpleName());
		sb.append('(');
		
		boolean isFirst = true;
		for (int e : vec.toArray())
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