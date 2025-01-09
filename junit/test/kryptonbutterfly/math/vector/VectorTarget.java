package test.kryptonbutterfly.math.vector;

import kryptonbutterfly.math.vector.IVector;

public record VectorTarget<Vec extends IVector<?>>(Class<? extends Vec> target, int dimensions)
{
	public static final int MAX_TEST_DIMS = 20;
}
