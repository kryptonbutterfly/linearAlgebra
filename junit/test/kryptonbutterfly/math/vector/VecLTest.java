package test.kryptonbutterfly.math.vector;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import kryptonbutterfly.math.vector._long.IVecL;
import kryptonbutterfly.math.vector._long.Vec2l;
import kryptonbutterfly.math.vector._long.Vec3l;
import kryptonbutterfly.math.vector._long.VecNl;
import lombok.SneakyThrows;
import test.kryptonbutterfly.math.Targets;

public class VecLTest
{
	private final Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <Vec extends IVecL<Vec>> Vec genVec(IntToLongFunction op, Class<Vec> t, int dims)
	{
		final var constructor = t.getConstructors()[0];
		if (constructor.isVarArgs())
		{
			final var values = new long[dims];
			for (int i : range(dims))
				values[i] = op.applyAsLong(i);
			return (Vec) constructor.newInstance(values);
		}
		final var values = new Object[dims];
		for (int i : range(dims))
			values[i] = op.applyAsLong(i);
		return (Vec) constructor.newInstance(values);
	}
	
	private static <Vec extends IVecL<Vec>> IntToLongFunction perform(Vec first, Vec second, LongBinaryOperator op)
	{
		return i -> op.applyAsLong(first.get(i), second.get(i));
	}
	
	@Test
	public void add()
	{
		final LongBinaryOperator op = (a, b) -> a + b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2l.class, 2);
		assertEquals(e2, f2.add(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3l.class, 3);
		assertEquals(e3, f3.add(s3));
	}
	
	@Test
	public void sub()
	{
		final LongBinaryOperator op = (a, b) -> a - b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2l.class, 2);
		assertEquals(e2, f2.sub(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3l.class, 3);
		assertEquals(e3, f3.sub(s3));
	}
	
	@Test
	public void get()
	{
		final long	x	= randNum();
		final long	y	= randNum();
		final long	z	= randNum();
		
		final var	v2	= new Vec2l(x, y);
		final var	v3	= new Vec3l(x, y, z);
		
		assertEquals(x, v2.get(0));
		assertEquals(x, v3.get(0));
		
		assertEquals(y, v2.get(1));
		assertEquals(y, v3.get(1));
		
		assertEquals(z, v3.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> v2.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> v3.get(3));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> new VecNl().get(0));
	}
	
	@Test
	public void toArray()
	{
		final long	x	= randNum();
		final long	y	= randNum();
		final long	z	= randNum();
		final long	w	= randNum();
		
		final var	v0	= new VecNl();
		final var	v2	= new Vec2l(x, y);
		final var	v3	= new Vec3l(x, y, z);
		final var	v4	= new VecNl(x, y, z, w);
		
		assertArrayEquals(new long[] {}, v0.toArray());
		assertArrayEquals(new long[] { x, y }, v2.toArray());
		assertArrayEquals(new long[] { x, y, z }, v3.toArray());
		assertArrayEquals(new long[] { x, y, z, w }, v4.toArray());
	}
	
	@Test
	public void dotProduct()
	{
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		long		dp2	= 0;
		for (int i : range(f2.dimensions()))
			dp2 += f2.get(i) * s2.get(i);
		assertEquals(dp2, f2.dotProduct(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		long		dp3	= 0;
		for (int i : range(f3.dimensions()))
			dp3 += f3.get(i) * s3.get(i);
		assertEquals(dp3, f3.dotProduct(s3));
	}
	
	@Test
	public void length()
	{
		final var	v2		= randVec2();
		long		len2	= 0;
		for (int i : range(v2.dimensions()))
			len2 += v2.get(i) * v2.get(i);
		assertEquals(len2, v2.lengthSQ());
		assertEquals(Math.sqrt(len2), v2.length());
		
		final var	v3		= randVec2();
		long		len3	= 0;
		for (int i : range(v3.dimensions()))
			len3 += v3.get(i) * v3.get(i);
		assertEquals(len3, v3.lengthSQ());
		assertEquals(Math.sqrt(len3), v3.length());
	}
	
	@TestFactory
	Stream<DynamicTest> addN()
	{
		final LongBinaryOperator op = (a, b) -> a + b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNl[%d].add".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNl.class, n);
				final var second = genVec(_i -> randNum(), VecNl.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNl.class, n);
				
				assertEquals(expected, first.add(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> subN()
	{
		final LongBinaryOperator op = (a, b) -> a - b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNl[%d].sub".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNl.class, n);
				final var second = genVec(_i -> randNum(), VecNl.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNl.class, n);
				
				assertEquals(expected, first.sub(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> getN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNl[%d].get".formatted(n), () ->
			{
				final var values = new long[n];
				for (int i : range(n))
					values[i] = randNum();
				final var vec = new VecNl(values);
				for (int i : range(n))
					assertEquals(values[i], vec.get(i));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> dotProductN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNl[%d]".formatted(n), () ->
			{
				final var vf = randArray(n);
				final var vs = randArray(n);
				long	dp	= 0;
				for (int i : range(n))
					dp += vf[i] * vs[i];
				assertEquals(dp, new VecNl(vf).dotProduct(new VecNl(vs)));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> lengthN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("‖VecNl[%d]‖".formatted(n), () ->
			{
				final var v	= randArray(n);
				long	len	= 0;
				for (int i : range(n))
					len += v[i] * v[i];
				assertEquals(len, new VecNl(v).lengthSQ());
				assertEquals(Math.sqrt(len), new VecNl(v).length());
			}));
	}
	
	private long[] randArray(int dimensions)
	{
		final var result = new long[dimensions];
		for (int i : range(dimensions))
			result[i] = randNum();
		return result;
	}
	
	private long randNum()
	{
		return rand.nextLong(Long.MIN_VALUE / 2, Long.MAX_VALUE / 2);
	}
	
	private Vec2l randVec2()
	{
		return new Vec2l(randNum(), randNum());
	}
	
	private Vec3l randVec3()
	{
		return new Vec3l(randNum(), randNum(), randNum());
	}
}