package kryptonbutterfly.math.matrix._float;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.vector._float.Vec4f;

public final class MatrixF4x4 extends AMatrixF<Vec4f, MatrixF4x4>
{
	/* @formatter:off */
	public static final MatrixF4x4 IDENTITY = new MatrixF4x4(
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1);
	/* @formatter:on */
	
	public MatrixF4x4(float... data)
	{
		super(4, 4, data);
	}
	
	@Override
	protected MatrixF4x4 create(int width, int height, float[] data)
	{
		return new MatrixF4x4(data);
	}
	
	@Override
	protected void mulAdd(float[] target, int targetIndex, int thisIndex, float[] right, int rightIndex)
	{
		target[targetIndex] += data[thisIndex] * right[rightIndex];
	}
	
	@Override
	public Vec4f mul(Vec4f vec)
	{
		return mul(vec, d -> new Vec4f(d[0], d[1], d[2], d[3]));
	}
	
	public MatrixF4x4 rotateZ(float rad)
	{
		final float	c	= (float) Math.cos(rad);
		final float	s	= (float) Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixF4x4(
			c, -s, 0, 0,
			s, c, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	public MatrixF4x4 rotateY(float rad)
	{
		final float	c	= (float) Math.cos(rad);
		final float	s	= (float) Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixF4x4(
			c, 0, -s, 0,
			0, 1, 0, 0,
			s, 0, c, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	public MatrixF4x4 rotateX(float rad)
	{
		final float	c	= (float) Math.cos(rad);
		final float	s	= (float) Math.sin(rad);
		/* @formatter:off */
		return this.mul(new MatrixF4x4(
			1, 0, 0, 0,
			0, c, -s, 0,
			0, s, c, 0,
			0, 0, 0, 1));
		/* @formatter:on */
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return equals(obj, AlgebraSettings.settings.FLOAT_EPSILON);
	}
}
