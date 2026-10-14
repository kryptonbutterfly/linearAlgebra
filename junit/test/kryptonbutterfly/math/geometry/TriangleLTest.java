package test.kryptonbutterfly.math.geometry;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.LongSupplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.geometry.triangle.TriangleL;
import kryptonbutterfly.math.vector._long.Vec2l;
import test.kryptonbutterfly.math.TestSettings;

public final class TriangleLTest
{
	private static Logger log = LoggerFactory.getLogger(TriangleLTest.class);
	
	private static final int	MAX_ATTEMPTS	= 100;
	private static final long	Y_FALLBACK		= -1000000;
	private final Random		rand			= new Random();
	
	private static final long				size		= 5;
	private static final TriangleL<Vec2l>	UNIT_TRI	= new TriangleL<>(
		new Vec2l(0, 0),
		new Vec2l(size, 0),
		new Vec2l(0, size));
	
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
		final long	x		= rand.nextLong(0, size);
		final long	y		= rand.nextLong(size - x);
		final var	point	= new Vec2l(x, y);
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
		return isOutside("unrestricted", 100, Long.MIN_VALUE, Long.MAX_VALUE);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMinOutside()
	{
		return isOutside("min", 100, Long.MIN_VALUE, Long.MIN_VALUE + size);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMaxOutside()
	{
		return isOutside("max", 100, Long.MAX_VALUE - size, Long.MAX_VALUE);
	}
	
	@TestFactory
	public Stream<DynamicTest> isBorderOutside()
	{
		return isOutside("border", 100, Long.MAX_VALUE - size, Long.MIN_VALUE + size);
	}
	
	private Stream<DynamicTest> isOutside(String variant, int repeat, long min, long max)
	{
		final LongSupplier randL;
		if (min < max)
			randL = () -> rand.nextLong(min, max);
		else
			randL = () -> rand.nextLong(Long.MIN_VALUE, Long.MIN_VALUE + min - max) + Long.MIN_VALUE;
		
		final var	tests	= new ArrayList<DynamicTest>();
		int			skipped	= 0;
		for (int i : range(repeat))
		{
			final var point = outside(randL);
			if (point == null)
				skipped++;
			else
				tests.add(
					DynamicTest.dynamicTest(
						"#%d outside 2l %s %s".formatted(i, variant, point),
						() -> assertFalse(UNIT_TRI.isInside(point))));
		}
		
		if (skipped > 0)
		{
			final int	SKIPPED			= skipped;
			final float	skippedPercent	= skipped * 100F / repeat;
			log.warn(
				() -> "Skipping %.1f%% (%d/%d) of randomized tests for outside 2l %s.%n".formatted(
					skippedPercent,
					SKIPPED,
					repeat,
					variant));
		}
		
		return tests.stream();
	}
	
	private Vec2l outside(LongSupplier randL)
	{
		final long	x		= randL.getAsLong();
		long		y		= 0;
		int			counter	= 0;
		
		final var	bigX	= new BigDecimal(x);
		BigDecimal	bigY;
		final var	bigSize	= new BigDecimal(size);
		final var	IITE	= new BigDecimal(AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON);
		do
		{
			y = randL.getAsLong();
			
			if (counter++ >= TestSettings.MAX_RETRIES())
				return null;
			
			bigY = new BigDecimal(y);
		}
		while (bigX.compareTo(IITE.negate()) >= 0
				&& bigY.compareTo(IITE.negate()) >= 0
				&& bigX.add(bigY).compareTo(IITE.add(bigSize)) <= 0);
		
		return new Vec2l(x, y);
	}
}
