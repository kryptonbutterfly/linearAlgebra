package kryptonbutterfly.math.vector._float;

public record Vec4f(float x, float y, float z, float w) implements IVecF<Vec4f>
{
	@Override
	public Vec4f add(Vec4f other)
	{
		return new Vec4f(
			x + other.x,
			y + other.y,
			z + other.z,
			w + other.w);
	}
	
	@Override
	public Vec4f sub(Vec4f other)
	{
		return new Vec4f(
			x - other.x,
			y - other.y,
			z - other.z,
			w - other.w);
	}
	
	@Override
	public Vec4f scale(double factor)
	{
		return new Vec4f(
			x * (float) factor,
			y * (float) factor,
			z * (float) factor,
			w * (float) factor);
	}
	
	@Override
	public int dimensions()
	{
		return 4;
	}
	
	@Override
	public float[] toArray()
	{
		return new float[] { x, y, z, w };
	}
	
	@Override
	public float get(int dimension)
	{
		return switch (dimension)
		{
			case 0 -> x;
			case 1 -> y;
			case 2 -> z;
			case 3 -> w;
			default -> throw indexOutOfBounds(dimension);
		};
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
