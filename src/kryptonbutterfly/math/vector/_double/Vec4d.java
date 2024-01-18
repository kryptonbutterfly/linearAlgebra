package kryptonbutterfly.math.vector._double;

public record Vec4d(double x, double y, double z, double w) implements IVecD<Vec4d>
{
	@Override
	public Vec4d add(Vec4d other)
	{
		return new Vec4d(x + other.x, y + other.y, z + other.z, w + other.w);
	}
	
	@Override
	public Vec4d sub(Vec4d other)
	{
		return new Vec4d(x - other.x, y - other.y, z - other.z, w - other.w);
	}
	
	@Override
	public Vec4d scale(double factor)
	{
		return new Vec4d(x * factor, y * factor, z * factor, w * factor);
	}
	
	@Override
	public int dimensions()
	{
		return 4;
	}
	
	@Override
	public double[] toArray()
	{
		return new double[] { x, y, z, w };
	}
	
	@Override
	public double get(int dimension)
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
		return IVecD.toString(this);
	}
}