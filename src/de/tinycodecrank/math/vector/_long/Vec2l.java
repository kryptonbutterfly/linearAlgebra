package de.tinycodecrank.math.vector._long;

public record Vec2l(long x, long y) implements IVecL<Vec2l>
{
	@Override
	public Vec2l add(Vec2l other)
	{
		return new Vec2l(x + other.x, y + other.y);
	}
	
	@Override
	public Vec2l sub(Vec2l other)
	{
		return new Vec2l(x - other.x, y - other.y);
	}
	
	@Override
	public Vec2l scale(double factor)
	{
		return new Vec2l((long) (x * factor), (long) (y * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 2;
	}
	
	@Override
	public long[] toArray()
	{
		return new long[] { x, y };
	}
	
	@Override
	public long get(int dimension)
	{
		return switch (dimension)
		{
		case 0 -> x;
		case 1 -> y;
		default -> throw indexOutOfBounds(dimension);
		};
	}
	
	@Override
	public String toString()
	{
		return IVecL.toString(this);
	}
}