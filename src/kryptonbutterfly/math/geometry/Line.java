package kryptonbutterfly.math.geometry;

import kryptonbutterfly.math.vector.IVector;

public class Line<Vec extends IVector<Vec>>
{
	private final Vec support, direction;
	
	public Line(Vec support, Vec direction)
	{
		this.support	= support;
		this.direction	= direction;
	}
	
	public Vec support()
	{
		return this.support;
	}
	
	public Vec direction()
	{
		return this.direction;
	}
	
	public double distance(Vec target)
	{
		return target.sub(target.sub(this.support).projectOn(this.direction)).length();
	}
}
