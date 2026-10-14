package kryptonbutterfly.math.vector._int;

public record Vec4i(int x, int y, int z, int w) implements IVecI<Vec4i>
{
	@Override
	public Vec4i add(Vec4i other)
	{
		return new Vec4i(x + other.x, y + other.y, z + other.z, w + other.w);
	}
	
	@Override
	public Vec4i sub(Vec4i other)
	{
		return new Vec4i(x - other.x, y - other.y, z - other.z, w - other.w);
	}
	
	@Override
	public Vec4i scale(double factor)
	{
		return new Vec4i((int) (x * factor), (int) (y * factor), (int) (z * factor), (int) (w * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 4;
	}
	
	@Override
	public int[] toArray()
	{
		return new int[] { x, y, z, w };
	}
	
	@Override
	public int get(int dimension)
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
