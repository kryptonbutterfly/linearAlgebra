package test.kryptonbutterfly.math.geometry;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.geometry.triangle.TriangleD;
import kryptonbutterfly.math.vector._double.Vec2d;
import test.kryptonbutterfly.math.TestSettings;

public final class TriangleDTest
{
	private static Logger log = LoggerFactory.getLogger(TriangleFTest.class);
	
	private static final int	MAX_ATTEMPTS	= 100;
	private static final double	Y_FALLBACK		= -1000000D;
	private final Random		rand			= new Random();
	
	private static final TriangleD<Vec2d> UNIT_TRI = new TriangleD<>(new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(0, 1));
	
	private static final double	MIN	= -1000_000_000;
	private static final double	MAX	= 1000_000_000;
	
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
		final double	x		= rand.nextDouble();
		final double	y		= rand.nextDouble(1 - x);
		final var		point	= new Vec2d(x, y);
		assertTrue(UNIT_TRI.isInside(point), "point: %s".formatted(point));
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutsideNear()
	{
		return isOutside("near", 100, -0.1, 1.1);
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutside()
	{
		return isOutside("unrestricted", 100, MIN, MAX);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMinOutside()
	{
		return isOutside("min", 100, MIN, MIN + 1);
	}
	
	@TestFactory
	public Stream<DynamicTest> isMaxOutside()
	{
		return isOutside("max", 100, MAX - 1, MAX);
	}
	
	@TestFactory
	public Stream<DynamicTest> isBorderOutside()
	{
		return isOutside("border", 100, MAX - 1, MIN + 1);
	}
	
	private Stream<DynamicTest> isOutside(String variant, int repeat, double min, double max)
	{
		final DoubleSupplier randD;
		if (min < max)
			randD = () -> rand.nextDouble(min, max);
		else
			randD = () -> rand.nextDouble(MIN, MIN + min - max) + MIN;
		
		final var	test	= new ArrayList<DynamicTest>();
		int			skipped	= 0;
		for (int i : range(repeat))
		{
			final var point = outside(randD);
			if (point == null)
				skipped++;
			else
				test.add(
					DynamicTest.dynamicTest(
						"#%d outside 2d %s %s".formatted(i, variant, point),
						() -> assertFalse(UNIT_TRI.isInside(point))));
		}
		
		if (skipped > 0)
		{
			final int	SKIPPED			= skipped;
			final float	skippedPercent	= skipped * 100F / repeat;
			log.warn(
				() -> "Skipping %.1f%% (%d/%d) of randomized tests for outside 2d %s%n".formatted(
					skippedPercent,
					SKIPPED,
					repeat,
					variant));
		}
		
		return test.stream();
	}
	
	private Vec2d outside(DoubleSupplier randD)
	{
		final double	x		= randD.getAsDouble();
		double			y		= 0;
		int				counter	= 0;
		
		final var	bigX	= new BigDecimal(x);
		BigDecimal	bigY;
		final var	bigSize	= new BigDecimal(1);
		final var	IITE	= new BigDecimal(AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON);
		do
		{
			y = randD.getAsDouble();
			
			if (counter++ >= TestSettings.MAX_RETRIES())
				return null;
			
			bigY = new BigDecimal(y);
		}
		while (bigX.compareTo(IITE.negate()) >= 0
				&& bigY.compareTo(IITE.negate()) >= 0
				&& bigX.add(bigY).compareTo(IITE.add(bigSize)) <= 0);
		
		return new Vec2d(x, y);
	}
}
