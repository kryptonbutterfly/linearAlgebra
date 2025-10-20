package test.kryptonbutterfly.math;

import static kryptonbutterfly.math.utils.range.Range.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import kryptonbutterfly.math.utils.range.Range;
import kryptonbutterfly.math.vector._int.Vec2i;
import kryptonbutterfly.math.vector._int.Vec3i;

public final class Targets
{
	private static final Range testMatDims = range(1, 15);
	
	private static final int	MIN_VEC_TEST_DIMS	= 0;
	private static final int	MAX_VEC_TEST_DIMS	= 20;
	
	public static IntStream createDimsStream()
	{
		return IntStream.range(MIN_VEC_TEST_DIMS, MAX_VEC_TEST_DIMS);
	}
	
	public static Stream<Vec2i> createDimsStream2()
	{
		final var sb = Stream.<Vec2i>builder();
		for (final int y : testMatDims)
			for (final int x : testMatDims)
				sb.accept(new Vec2i(x, y));
		return sb.build();
	}
	
	public static Stream<Vec3i> createDimsStream3()
	{
		final var sb = Stream.<Vec3i>builder();
		for (final int z : testMatDims)
			for (final int y : testMatDims)
				for (final int x : testMatDims)
					sb.accept(new Vec3i(x, y, z));
		return sb.build();
	}
}
