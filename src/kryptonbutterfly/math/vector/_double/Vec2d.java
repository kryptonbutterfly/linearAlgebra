package kryptonbutterfly.math.vector._double;

public record Vec2d(double x, double y) implements IVecD<Vec2d>
{
	@Override
	public Vec2d add(Vec2d other)
	{
		return new Vec2d(x + other.x, y + other.y);
	}
	
	@Override
	public Vec2d sub(Vec2d other)
	{
		return new Vec2d(x - other.x, y - other.y);
	}
	
	@Override
	public Vec2d scale(double factor)
	{
		return new Vec2d(x * factor, y * factor);
	}
	
	@Override
	public int dimensions()
	{
		return 2;
	}
	
	@Override
	public double[] toArray()
	{
		return new double[] { x, y };
	}
	
	@Override
	public double get(int dimension)
	{
		return switch (dimension)
		{
			case 0 -> x;
			case 1 -> y;
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