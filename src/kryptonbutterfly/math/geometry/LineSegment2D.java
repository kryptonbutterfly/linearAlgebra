package kryptonbutterfly.math.geometry;

import kryptonbutterfly.math.vector._double.Vec2d;

public final class LineSegment2D
{
	private final Vec2d start, stop, orth;
	
	public LineSegment2D(Vec2d start, Vec2d stop)
	{
		this.start	= start;
		this.stop	= stop;
		this.orth	= rotate90(stop.sub(start).norm());
	}
	
	public Vec2d start()
	{
		return this.start;
	}
	
	public Vec2d stop()
	{
		return this.stop;
	}
	
	public Vec2d orth()
	{
		return this.orth;
	}
	
	private static final Vec2d rotate90(Vec2d vec)
	{
		return new Vec2d(-vec.y(), vec.x());
	}
	
	public boolean isInFront(Vec2d target)
	{
		return 0 >= orth.dotProduct(target.sub(start));
	}
}