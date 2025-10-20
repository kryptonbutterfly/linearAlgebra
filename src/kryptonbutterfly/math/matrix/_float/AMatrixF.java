package kryptonbutterfly.math.matrix._float;

import java.util.function.Function;

import kryptonbutterfly.math.matrix.AMatrix;
import kryptonbutterfly.math.vector._float.IVecF;

public abstract class AMatrixF<Vec extends IVecF<Vec>, Mat extends AMatrixF<Vec, Mat>> extends AMatrix<Vec, Mat, float[]>
{
	protected AMatrixF(int width, int height, float[] data)
	{
		super(width, height, data, float[]::new);
	}
	
	@Override
	protected void mulAdd(float[] target, int targetIndex, int thisIndex, float[] right, int rightIndex)
	{
		target[targetIndex] += data[thisIndex] * right[rightIndex];
	}
	
	final Vec mul(Vec vec, Function<float[], Vec> createArray)
	{
		assertVecDims(width, vec.dimensions());
		
		final var result = new float[height];
		for (int i = 0; i < width; i++)
		{
			final float t = vec.get(i);
			for (int d = 0; d < height; d++)
				result[d] += t * data[d * width + i];
		}
		return createArray.apply(result);
	}
}
