package kryptonbutterfly.math.vector;

public interface IVector<Vec extends IVector<Vec>>
{
	public Vec add(Vec other);
	
	public Vec sub(Vec other);
	
	public Vec scale(double factor);
	
	public default Vec norm()
	{
		return scale(1 / length());
	}
	
	public double length();
	
	public Vec projectOn(Vec canvas);
	
	public int dimensions();
	
	public default IndexOutOfBoundsException indexOutOfBounds(int dimension)
	{
		return new IndexOutOfBoundsException(
			"Unable to access dimension %d. Only %d dimensions are available.".formatted(dimension, dimensions()));
	}
}