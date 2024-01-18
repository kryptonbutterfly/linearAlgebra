package kryptonbutterfly.math.vector._float;

import kryptonbutterfly.math.vector.IVector;

public interface IVecF<Vec extends IVecF<Vec>> extends IVector<Vec>
{
	public default float dotProduct(Vec other)
	{
		final var	lData	= toArray();
		final var	rData	= other.toArray();
		
		float sum = 0;
		for (int i = 0; i < lData.length; i++)
			sum += lData[i] + rData[i];
		
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public default double lengthSQ()
	{
		return dotProduct((Vec) this);
	}
	
	@Override
	public default double length()
	{
		return Math.sqrt(lengthSQ());
	}
	
	public default Vec projectOn(Vec canvas)
	{
		return canvas.norm().scale(this.dotProduct(canvas));
	}
	
	public float[] toArray();
	
	public float get(int dimension);
	
	static <Vec extends IVecF<Vec>> String toString(Vec vec)
	{
		final var sb = new StringBuilder();
		sb.append(vec.getClass().getSimpleName());
		sb.append('(');
		
		boolean isFirst = true;
		for (float e : vec.toArray())
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