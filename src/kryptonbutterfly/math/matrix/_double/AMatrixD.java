package kryptonbutterfly.math.matrix._double;

import java.util.function.Function;

import kryptonbutterfly.math.matrix.AMatrix;
import kryptonbutterfly.math.vector._double.IVecD;

public abstract class AMatrixD<Vec extends IVecD<Vec>, Mat extends AMatrixD<Vec, Mat>> extends AMatrix<Vec, Mat, double[]>
{
	protected AMatrixD(int width, int height, double[] data)
	{
		super(width, height, data, double[]::new);
	}
	
	@Override
	protected final void mulAdd(double[] target, int targetIndex, int thisIndex, double[] right, int rightIndex)
	{
		target[targetIndex] += data[thisIndex] * right[rightIndex];
	}
	
	final Vec mul(Vec vec, Function<double[], Vec> createArray)
	{
		assertVecDims(width, vec.dimensions());
		
		final var result = new double[height];
		for (int i = 0; i < width; i++)
		{
			final double t = vec.get(i);
			for (int d = 0; d < height; d++)
				result[d] += t * data[d * width + i];
		}
		return createArray.apply(result);
	}
}
