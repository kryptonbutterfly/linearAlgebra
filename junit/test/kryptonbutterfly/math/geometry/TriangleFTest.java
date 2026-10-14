package test.kryptonbutterfly.math.geometry;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import kryptonbutterfly.functions.float_.FloatSupplier;
import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.geometry.triangle.TriangleF;
import kryptonbutterfly.math.vector._float.Vec2f;
import test.kryptonbutterfly.math.TestSettings;

public final class TriangleFTest
{
	private static Logger log = LoggerFactory.getLogger(TriangleFTest.class);
	
	private static final float	Y_FALLBACK	= -1000000F;
	private final Random		rand		= new Random();
	
	private static final TriangleF<Vec2f> UNIT_TRI = new TriangleF<>(new Vec2f(0, 0), new Vec2f(1, 0), new Vec2f(0, 1));
	
	private static final float	MIN	= -1000_000;
	private static final float	MAX	= +1000_000;
	
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
		final float	x		= rand.nextFloat();
		final float	y		= rand.nextFloat(1 - x);
		final var	point	= new Vec2f(x, y);
		assertTrue(UNIT_TRI.isInside(point), "point: %s".formatted(point));
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutsideNear()
	{
		return isOutside("near", 100, -0.1F, 1.1F);
	}
	
	@TestFactory
	public Stream<DynamicTest> isOutSide()
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
	
	private Stream<DynamicTest> isOutside(String variant, int repeat, float min, float max)
	{
		final FloatSupplier randF;
		if (min < max)
			randF = () -> rand.nextFloat(min, max);
		else
			randF = () -> rand.nextFloat(MIN, MIN + min - max) + MIN;
		
		final var	tests	= new ArrayList<DynamicTest>();
		int			skipped	= 0;
		for (int i : range(repeat))
		{
			final var point = outside(randF);
			if (point == null)
				skipped++;
			else
				tests.add(
					DynamicTest.dynamicTest(
						"#%d outside 2f %s %s".formatted(i, variant, point),
						() -> assertFalse(UNIT_TRI.isInside(point))));
		}
		if (skipped > 0)
		{
			final int	SKIPPED			= skipped;
			final float	skippedPercent	= skipped * 100F / repeat;
			log.warn(
				() -> "Skipping %.1f%% (%d/%d) of randomized tests for outside 2f %s.%n".formatted(
					skippedPercent,
					SKIPPED,
					repeat,
					variant));
		}
		return tests.stream();
	}
	
	private Vec2f outside(FloatSupplier randF)
	{
		final float	x		= randF.getAsFloat();
		float		y		= 0;
		int			counter	= 0;
		
		final var	bigX	= new BigDecimal(x);
		BigDecimal	bigY;
		final var	bigSize	= new BigDecimal(1);
		final var	IITE	= new BigDecimal(AlgebraSettings.settings.IS_INSIDE_TRIANGLE_EPSILON);
		do
		{
			y = randF.getAsFloat();
			
			if (counter++ >= TestSettings.MAX_RETRIES())
				return null;
			
			bigY = new BigDecimal(y);
		}
		while (bigX.compareTo(IITE.negate()) >= 0
				&& bigY.compareTo(IITE.negate()) >= 0
				&& bigX.add(bigY).compareTo(IITE.add(bigSize)) <= 0);
		
		return new Vec2f(x, y);
	}
}
