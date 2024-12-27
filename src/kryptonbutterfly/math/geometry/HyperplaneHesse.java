package kryptonbutterfly.math.geometry;

import kryptonbutterfly.math.vector._double.IVecD;

public class HyperplaneHesse<Vec extends IVecD<Vec>>
{
	private final Vec support, normal;
	
	/**
	 * @param support
	 *            A Point inside the hyperplane.
	 * @param normal
	 *            A Vector orthogonal to the hyperplane.
	 * 			
	 *            Creates a hyperplane using the given vectors. Normalizes the
	 *            second vector.
	 */
	public HyperplaneHesse(Vec support, Vec normal)
	{
		this.support	= support;
		this.normal		= normal.norm();
	}
	
	/**
	 * @return A point inside the hyperplane.
	 */
	public final Vec support()
	{
		return this.support;
	}
	
	/**
	 * @return A normalized Vector orthogonal to the hyperplane.
	 */
	public final Vec normal()
	{
		return this.normal;
	}
	
	/**
	 * 
	 * @param target
	 *            A Point.
	 * @return The distance between the provided point and this plane.
	 */
	public double distance(Vec target)
	{
		return this.normal.dotProduct(target.sub(this.support));
	}
}
