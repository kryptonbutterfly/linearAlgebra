package kryptonbutterfly.math.matrix._double;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.vector._double.Vec4d;

public final class MatrixD4x4 extends AMatrixD<Vec4d, MatrixD4x4>
{
	/* @formatter:off */
	public static final MatrixD4x4 IDENTITY = new MatrixD4x4(
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1);
	/* @formatter:on */
	
	public MatrixD4x4(double... data)
	{
		super(4, 4, data);
	}
	
	@Override
	protected MatrixD4x4 create(int width, int height, double[] data)
	{
		return new MatrixD4x4(data);
	}
	
	@Override
	public Vec4d mul(Vec4d vec)
	{
		return mul(vec, d -> new Vec4d(d[0], d[1], d[2], d[3]));
	}
	
	public MatrixD4x4 rotateZ(double rad)
	{
		final double	c	= Math.cos(rad);
		final double	s	= Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixD4x4(
			c, -s, 0, 0,
			s, c, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	public MatrixD4x4 rotateY(double rad)
	{
		final double	c	= Math.cos(rad);
		final double	s	= Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixD4x4(
			c, 0, -s, 0,
			0, 1, 0, 0,
			s, 0, c, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	public MatrixD4x4 rotateX(double rad)
	{
		final double	c	= Math.cos(rad);
		final double	s	= Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixD4x4(
			1, 0, 0, 0,
			0, c, -s, 0,
			0, s, c, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return equals(obj, AlgebraSettings.settings.DOUBLE_EPSILON);
	}
}
