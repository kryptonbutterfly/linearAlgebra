package kryptonbutterfly.math.vector._int;

public record Vec3i(int x, int y, int z) implements IVecI<Vec3i>
{
	@Override
	public Vec3i add(Vec3i other)
	{
		return new Vec3i(x + other.x, y + other.y, z + other.z);
	}
	
	@Override
	public Vec3i sub(Vec3i other)
	{
		return new Vec3i(x - other.x, y - other.y, z - other.z);
	}
	
	@Override
	public Vec3i scale(double factor)
	{
		return new Vec3i((int) (x * factor), (int) (y * factor), (int) (z * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 3;
	}
	
	@Override
	public int[] toArray()
	{
		return new int[] { x, y, z };
	}
	
	@Override
	public int get(int dimension)
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
		return IVecI.toString(this);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return IVecI.equals(this, other);
	}
	
	@Override
	public int hashCode()
	{
		return IVecI.hashCode(this);
	}
}