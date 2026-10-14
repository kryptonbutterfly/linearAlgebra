package test.kryptonbutterfly.math.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kryptonbutterfly.math.geometry.triangle.TriangleI;
import kryptonbutterfly.math.vector._int.VecNi;

public final class TriangleI0dTest
{
	final VecNi				point		= new VecNi();
	final TriangleI<VecNi>	triangle	= new TriangleI<>(point, point, point);
	
	@Test
	public void isInside()
	{
		assertEquals(0, point.dimensions());
		
		assertTrue(triangle.isInside(point));
	}
	
	@Test
	public void isOutside()
	{
		assertFalse(triangle.isOutSide(point));
	}
}
