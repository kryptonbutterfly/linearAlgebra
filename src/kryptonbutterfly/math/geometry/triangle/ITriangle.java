package kryptonbutterfly.math.geometry.triangle;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.vector.IVector;

public interface ITriangle<Vertex extends IVector<Vertex>>
{
	Vertex vert1();
	
	Vertex vert2();
	
	Vertex vert3();
	
	public ITriangle<Vertex> create(Vertex v1, Vertex v2, Vertex v3);
	
	public default double area()
	{
		final var	u	= vert2().sub(vert1());
		final var	v	= vert3().sub(vert1());
		
		final double dotUV = u.dotProduct(v);
		
		return Math.sqrt(u.lengthSQ() * v.lengthSQ() - dotUV * dotUV) * 0.5;
	}
	
	public default double isInsideRaw(Vertex point)
	{
		return create(point, vert2(), vert3()).area()
			+ create(vert1(), point, vert3()).area()
			+ create(vert1(), vert2(), point).area();
	}
	
	public default boolean isInside(Vertex point)
	{
		final double	raw		= isInsideRaw(point);
		final double	area	= area();
		return raw < area + AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON;
	}
	
	public default boolean isOutSide(Vertex point)
	{
		final double	raw		= isInsideRaw(point);
		final double	area	= area();
		return raw > area + AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON;
	}
}
