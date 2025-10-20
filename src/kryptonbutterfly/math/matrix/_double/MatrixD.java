package kryptonbutterfly.math.matrix._double;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.vector._double.VecNd;

public final class MatrixD extends AMatrixD<VecNd, MatrixD>
{
	public MatrixD(int width, int height, double... data)
	{
		super(width, height, data);
	}
	
	@Override
	protected MatrixD create(int width, int height, double[] data)
	{
		return new MatrixD(width, height, data);
	}
	
	@Override
	public VecNd mul(VecNd vec)
	{
		return mul(vec, VecNd::new);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return equals(obj, AlgebraSettings.settings.DOUBLE_EPSILON);
	}
}
