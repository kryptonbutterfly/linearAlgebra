package kryptonbutterfly.math.vector._float;

public record Vec2f(float x, float y) implements IVecF<Vec2f>
{
	@Override
	public Vec2f add(Vec2f other)
	{
		return new Vec2f(x + other.x, y + other.y);
	}
	
	@Override
	public Vec2f sub(Vec2f other)
	{
		return new Vec2f(x - other.x, y - other.y);
	}
	
	@Override
	public Vec2f scale(double factor)
	{
		return new Vec2f((float) (x * factor), (float) (y * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 2;
	}
	
	@Override
	public float[] toArray()
	{
		return new float[] { x, y };
	}
	
	@Override
	public float get(int dimension)
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
		return IVecF.toString(this);
	}
}