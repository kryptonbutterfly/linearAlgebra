package kryptonbutterfly.math.vector._long;

public record Vec3l(long x, long y, long z) implements IVecL<Vec3l>
{
	@Override
	public Vec3l add(Vec3l other)
	{
		return new Vec3l(x + other.x, y + other.y, z + other.z);
	}
	
	@Override
	public Vec3l sub(Vec3l other)
	{
		return new Vec3l(x - other.x, y - other.y, z - other.z);
	}
	
	@Override
	public Vec3l scale(double factor)
	{
		return new Vec3l((long) (x + factor), (long) (y * factor), (long) (z * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 3;
	}
	
	@Override
	public long[] toArray()
	{
		return new long[] { x, y, z };
	}
	
	@Override
	public long get(int dimension)
	{
		return switch (dimension)
		{
			case 0 -> x;
			case 1 -> y;
			case 2 -> z;
			default -> throw indexOutOfBounds(dimension);
		};
	}
	
	@Override
	/**
	 * @return A string representation of {@code this} vector.
	 */
	public String toString()
	{
		return IVecL.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecL.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecL.hashCode(this);
	}
	
}