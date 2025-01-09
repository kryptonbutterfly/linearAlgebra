package kryptonbutterfly.math.vector._double;

public record Vec3d(double x, double y, double z) implements IVecD<Vec3d>
{
	@Override
	public Vec3d add(Vec3d other)
	{
		return new Vec3d(x + other.x, y + other.y, z + other.z);
	}
	
	@Override
	public Vec3d sub(Vec3d other)
	{
		return new Vec3d(x - other.x, y - other.y, z - other.z);
	}
	
	@Override
	public Vec3d scale(double factor)
	{
		return new Vec3d(x * factor, y * factor, z * factor);
	}
	
	@Override
	public int dimensions()
	{
		return 3;
	}
	
	@Override
	public double[] toArray()
	{
		return new double[] { x, y, z };
	}
	
	@Override
	public double get(int dimension)
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