package kryptonbutterfly.math;

public class DimensionalityMismatchException extends RuntimeException
{
	private static final long serialVersionUID = -6136859950720811757L;
	
	public DimensionalityMismatchException(String template, Object... args)
	{
		this(template.formatted(args));
	}
	
	public DimensionalityMismatchException(String message)
	{
		super(message);
	}
	
	public DimensionalityMismatchException(int expected, int provided)
	{
		this("Expected %d dimensions but %d where provided.", expected, provided);
	}
}