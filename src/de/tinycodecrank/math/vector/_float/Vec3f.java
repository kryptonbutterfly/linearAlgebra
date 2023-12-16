package de.tinycodecrank.math.vector._float;

public record Vec3f(float x, float y, float z) implements IVecF<Vec3f>
{
	@Override
	public Vec3f add(Vec3f other)
	{
		return new Vec3f(x + other.x, y + other.y, z + other.z);
	}
	
	@Override
	public Vec3f sub(Vec3f other)
	{
		return new Vec3f(x - other.x, y - other.y, z - other.z);
	}
	
	@Override
	public Vec3f scale(double factor)
	{
		return new Vec3f((float) (x * factor), (float) (y * factor), (float) (z * factor));
	}
	
	@Override
	public int dimensions()
	{
		return 3;
	}
	
	@Override
	public float[] toArray()
	{
		return new float[] { x, y, z };
	}
	
	@Override
	public float get(int dimension)
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
	public String toString()
	{
		return IVecF.toString(this);
	}
}