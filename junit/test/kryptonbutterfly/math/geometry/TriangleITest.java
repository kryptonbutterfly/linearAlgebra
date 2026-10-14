package test.kryptonbutterfly.math.geometry;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.geometry.triangle.TriangleI;
import kryptonbutterfly.math.vector._int.Vec2i;
import test.kryptonbutterfly.math.TestSettings;

public final class TriangleITest
{
	private static Logger log = LoggerFactory.getLogger(TriangleITest.class);
	
	private static final int	MAX_ATTEMPTS	= 100;
	private static final int	Y_FALLBACK		= -1000000;
	private final Random		rand			= new Random();
	
	private static final int				size		= 5;
	private static final TriangleI<Vec2i>	UNIT_TRI	= new TriangleI<>(
		new Vec2i(0, 0),
		new Vec2i(size, 0),
		new Vec2i(0, size));
	
	@BeforeAll
	public static void init()
	{
		AlgebraSettings.settings.FP_STRINGIFY_TEMPLATE = "%-7.2f";
	}
	
	@AfterAll
	public static void finish()
	{}
	
	@Test
	public void isInside()
	{
		final int	x		= rand.nextInt(0, size);
		final int	y		= rand.nextInt(0, size - x);
		final var	point	= new Vec2i(x, y);
		assertTrue(UNIT_TRI.isInside(point), "point: %s".formatted(point));
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutsideNear()
	{
		return isOutside("near", 100, -1, size + 1);
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutside()
	{
		return isOutside("unrestricted", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMinOutside()
	{
		return isOutside("min", 100, Integer.MIN_VALUE, Integer.MIN_VALUE + size);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMaxOutside()
	{
		var stream = isOutside("max", 100, Integer.MAX_VALUE - size, Integer.MAX_VALUE);
		return stream;
	}
	
	@TestFactory
	public Stream<DynamicTest> isBorderOutside()
	{
		return isOutside("border", 100, Integer.MAX_VALUE - size, Integer.MIN_VALUE + size);
	}
	
	private Stream<DynamicTest> isOutside(String variant, int repeat, int min, int max)
	{
		final IntSupplier randI;
		if (min < max)
			randI = () -> rand.nextInt(min, max);
		else
			randI = () -> rand.nextInt(Integer.MIN_VALUE, Integer.MIN_VALUE + min - max) + Integer.MIN_VALUE;
		
		final var	tests	= new ArrayList<DynamicTest>();
		int			skipped	= 0;
		for (int i : range(repeat))
		{
			final var point = outside(randI);
			if (point == null)
				skipped++;
			else
				tests.add(
					DynamicTest.dynamicTest(
						"#%d outside 2i %s %s".formatted(i, variant, point),
						() -> assertFalse(UNIT_TRI.isInside(point))));
		}
		
		if (skipped > 0)
		{
			final int	SKIPPED			= skipped;
			final float	skippedPercent	= skipped * 100F / repeat;
			log.warn(
				() -> "Skipping %.1f%% (%d/%d) of randomized tests for outside 2i %s.%n".formatted(
					skippedPercent,
					SKIPPED,
					repeat,
					variant));
		}
		
		return tests.stream();
	}
	
	private Vec2i outside(IntSupplier randI)
	{
		final int	x		= randI.getAsInt();
		int			y		= 0;
		int			counter	= 0;
		
		final var	bigX	= new BigDecimal(x);
		BigDecimal	bigY;
		final var	bigSize	= new BigDecimal(size);
		final var	IITE	= new BigDecimal(AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON);
		do
		{
			y = randI.getAsInt();
			
			if (counter++ >= TestSettings.MAX_RETRIES())
				return null;
			
			bigY = new BigDecimal(y);
		}
		while (bigX.compareTo(IITE.negate()) >= 0
				&& bigY.compareTo(IITE.negate()) >= 0
				&& bigX.add(bigY).compareTo(IITE.add(bigSize)) <= 0);
		
		return new Vec2i(x, y);
	}
}
