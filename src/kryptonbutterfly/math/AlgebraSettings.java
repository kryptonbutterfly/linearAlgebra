package kryptonbutterfly.math;

public final class AlgebraSettings
{
	public static final AlgebraSettings settings = new AlgebraSettings();
	
	private AlgebraSettings()
	{
		if (settings != null)
			throw new RuntimeException(new IllegalAccessException("Do NOT create instances of this class!!!"));
	}
	
	/**
	 * The maximum allowed difference between two doubles, in order to be considered
	 * equal.
	 */
	public double DOUBLE_EPSILON = 1D / 10_000D;
	
	/**
	 * The maximum allowed difference between two floats, in order to be considered
	 * equal.
	 */
	public double FLOAT_EPSILON = 1F / 1_000F;
	
	/**
	 * The template to use when stringifying floatingpoint numbers in toString
	 * methods.
	 */
	public String FP_STRINGIFY_TEMPLATE = "%f";
}
