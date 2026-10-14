package kryptonbutterfly.math.geometry.triangle;

import kryptonbutterfly.math.vector._float.IVecF;

public record TriangleF<Vertex extends IVecF<Vertex>>(
	Vertex vert1,
	Vertex vert2,
	Vertex vert3) implements ITriangle<Vertex>
{
	
	@Override
	public ITriangle<Vertex> create(Vertex v1, Vertex v2, Vertex v3)
	{
		return new TriangleF<>(v1, v2, v3);
	}
}
