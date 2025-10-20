package kryptonbutterfly.math.matrix._float;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.vector._float.VecNf;

public final class MatrixF extends AMatrixF<VecNf, MatrixF>
{
	public MatrixF(int width, int height, float... data)
	{
		super(width, height, data);
	}
	
	@Override
	protected MatrixF create(int width, int height, float[] data)
	{
		return new MatrixF(width, height, data);
	}
	
	@Override
	protected void mulAdd(float[] target, int targetIndex, int thisIndex, float[] right, int rightIndex)
	{
		target[targetIndex] += data[thisIndex] * right[rightIndex];
	}
	
	@Override
	public VecNf mul(VecNf vec)
	{
		return mul(vec, VecNf::new);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return equals(obj, AlgebraSettings.settings.FLOAT_EPSILON);
	}
}
