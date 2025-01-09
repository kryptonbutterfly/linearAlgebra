package kryptonbutterfly.math.vector._int;

public record Vec2i(int x, int y) implements IVecI<Vec2i>
{
	@Override
	public Vec2i add(Vec2i other)
	{
		return new Vec2i(x + other.x, y + other.y);
	}
	
	@Override
	public Vec2i sub(Vec2i other)
	{
		return new Vec2i(x - other.x, y - other.y);
	}
	
	@Override
	public Vec2i scale(double factor)
	{
		return new Vec2i((int) (x * factor), (int) (y * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 2;
	}
	
	@Override
	public int[] toArray()
	{
		return new int[] { x, y };
	}
	
	@Override
	public int get(int dimension)
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