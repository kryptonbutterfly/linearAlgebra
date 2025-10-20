package test.kryptonbutterfly.math.vector;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import kryptonbutterfly.math.vector._int.IVecI;
import kryptonbutterfly.math.vector._int.Vec2i;
import kryptonbutterfly.math.vector._int.Vec3i;
import kryptonbutterfly.math.vector._int.VecNi;
import lombok.SneakyThrows;
import test.kryptonbutterfly.math.Targets;

public class VecITest
{
	private final Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <Vec extends IVecI<Vec>> Vec genVec(IntUnaryOperator op, Class<Vec> t, int dims)
	{
		final var constructor = t.getConstructors()[0];
		if (constructor.isVarArgs())
		{
			final var values = new int[dims];
			for (int i : range(dims))
				values[i] = op.applyAsInt(i);
			return (Vec) constructor.newInstance(values);
		}
		final var values = new Object[dims];
		for (int i : range(dims))
			values[i] = op.applyAsInt(i);
		return (Vec) constructor.newInstance(values);
	}
	
	private static <Vec extends IVecI<Vec>> IntUnaryOperator perform(Vec first, Vec second, IntBinaryOperator op)
	{
		return i -> op.applyAsInt(first.get(i), second.get(i));
	}
	
	@Test
	public void add()
	{
		final IntBinaryOperator op = (a, b) -> a + b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2i.class, 2);
		assertEquals(e2, f2.add(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3i.class, 3);
		assertEquals(e3, f3.add(s3));
	}
	
	@Test
	public void sub()
	{
		final IntBinaryOperator op = (a, b) -> a - b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2i.class, 2);
		assertEquals(e2, f2.sub(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3i.class, 3);
		assertEquals(e3, f3.sub(s3));
	}
	
	@Test
	public void get()
	{
		final int	x	= randNum();
		final int	y	= randNum();
		final int	z	= randNum();
		
		final var	v2	= new Vec2i(x, y);
		final var	v3	= new Vec3i(x, y, z);
		
		assertEquals(x, v2.get(0));
		assertEquals(x, v3.get(0));
		
		assertEquals(y, v2.get(1));
		assertEquals(y, v3.get(1));
		
		assertEquals(z, v3.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> v2.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> v3.get(3));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> new VecNi().get(0));
	}
	
	@Test
	public void toArray()
	{
		final int	x	= randNum();
		final int	y	= randNum();
		final int	z	= randNum();
		final int	w	= randNum();
		
		final var	v0	= new VecNi();
		final var	v2	= new Vec2i(x, y);
		final var	v3	= new Vec3i(x, y, z);
		final var	v4	= new VecNi(x, y, z, w);
		
		assertArrayEquals(new int[] {}, v0.toArray());
		assertArrayEquals(new int[] { x, y }, v2.toArray());
		assertArrayEquals(new int[] { x, y, z }, v3.toArray());
		assertArrayEquals(new int[] { x, y, z, w }, v4.toArray());
	}
	
	@Test
	public void dotProduct()
	{
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		int			dp2	= 0;
		for (int i : range(f2.dimensions()))
			dp2 += f2.get(i) * s2.get(i);
		assertEquals(dp2, f2.dotProduct(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		int			dp3	= 0;
		for (int i : range(f3.dimensions()))
			dp3 += f3.get(i) * s3.get(i);
		assertEquals(dp3, f3.dotProduct(s3));
	}
	
	@Test
	public void length()
	{
		final var	v2		= randVec2();
		int			len2	= 0;
		for (int i : range(v2.dimensions()))
			len2 += v2.get(i) * v2.get(i);
		assertEquals(len2, v2.lengthSQ());
		assertEquals(Math.sqrt(len2), v2.length());
		
		final var	v3		= randVec3();
		int			len3	= 0;
		for (int i : range(v3.dimensions()))
			len3 += v3.get(i) * v3.get(i);
		assertEquals(len3, v3.lengthSQ());
		assertEquals(Math.sqrt(len3), v3.length());
	}
	
	@TestFactory
	Stream<DynamicTest> addN()
	{
		final IntBinaryOperator op = (a, b) -> a + b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNi[%d].add".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNi.class, n);
				final var second = genVec(_i -> randNum(), VecNi.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNi.class, n);
				
				assertEquals(expected, first.add(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> subN()
	{
		final IntBinaryOperator op = (a, b) -> a - b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNi[%d].sub".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNi.class, n);
				final var second = genVec(_i -> randNum(), VecNi.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNi.class, n);
				
				assertEquals(expected, first.sub(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> getN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNi[%d].get".formatted(n), () ->
			{
				final var values = new int[n];
				for (int i : range(n))
					values[i] = randNum();
				final var vec = new VecNi(values);
				for (int i : range(n))
					assertEquals(values[i], vec.get(i));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> dotProductN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNi[%d]·".formatted(n), () ->
			{
				final var vf = randArray(n);
				final var vs = randArray(n);
				int		dp	= 0;
				for (int i : range(n))
					dp += vf[i] * vs[i];
				assertEquals(dp, new VecNi(vf).dotProduct(new VecNi(vs)));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> lengthN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("‖VecNi[%d]‖".formatted(n), () ->
			{
				final var v	= randArray(n);
				int		len	= 0;
				for (int i : range(n))
					len += v[i] * v[i];
				assertEquals(len, new VecNi(v).lengthSQ());
				assertEquals(Math.sqrt(len), new VecNi(v).length());
			}));
	}
	
	private int[] randArray(int dimensions)
	{
		final var result = new int[dimensions];
		for (int i : range(dimensions))
			result[i] = randNum();
		return result;
	}
	
	private int randNum()
	{
		return rand.nextInt(Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2);
	}
	
	private Vec2i randVec2()
	{
		return new Vec2i(randNum(), randNum());
	}
	
	private Vec3i randVec3()
	{
		return new Vec3i(randNum(), randNum(), randNum());
	}
}
