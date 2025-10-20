package test.kryptonbutterfly.math.vector;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import kryptonbutterfly.functions.float_.FloatBinaryOperator;
import kryptonbutterfly.functions.int_.IntToFloatFunction;
import kryptonbutterfly.math.vector._float.IVecF;
import kryptonbutterfly.math.vector._float.Vec2f;
import kryptonbutterfly.math.vector._float.Vec3f;
import kryptonbutterfly.math.vector._float.Vec4f;
import kryptonbutterfly.math.vector._float.VecNf;
import lombok.SneakyThrows;
import test.kryptonbutterfly.math.Targets;

public class VecFTest
{
	private final Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <Vec extends IVecF<Vec>> Vec genVec(IntToFloatFunction op, Class<Vec> t, int dims)
	{
		final var constructor = t.getConstructors()[0];
		if (constructor.isVarArgs())
		{
			final var values = new float[dims];
			for (int i : range(dims))
				values[i] = op.apply(i);
			return (Vec) constructor.newInstance(values);
		}
		final var values = new Object[dims];
		for (int i : range(dims))
			values[i] = op.apply(i);
		return (Vec) constructor.newInstance(values);
	}
	
	private static <Vec extends IVecF<Vec>> IntToFloatFunction perform(Vec first, Vec second, FloatBinaryOperator op)
	{
		return i -> op.apply(first.get(i), second.get(i));
	}
	
	@Test
	public void add()
	{
		final FloatBinaryOperator op = (a, b) -> a + b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2f.class, 2);
		assertEquals(e2, f2.add(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3f.class, 3);
		assertEquals(e3, f3.add(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		final var	e4	= genVec(perform(f4, s4, op), Vec4f.class, 4);
		assertEquals(e4, f4.add(s4));
	}
	
	@Test
	public void sub()
	{
		final FloatBinaryOperator op = (a, b) -> a - b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2f.class, 2);
		assertEquals(e2, f2.sub(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3f.class, 3);
		assertEquals(e3, f3.sub(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		final var	e4	= genVec(perform(f4, s4, op), Vec4f.class, 4);
		assertEquals(e4, f4.sub(s4));
	}
	
	@Test
	public void get()
	{
		final float	x	= randNum();
		final float	y	= randNum();
		final float	z	= randNum();
		final float	w	= randNum();
		
		final var	v2	= new Vec2f(x, y);
		final var	v3	= new Vec3f(x, y, z);
		final var	v4	= new Vec4f(x, y, z, w);
		
		assertEquals(x, v2.get(0));
		assertEquals(x, v3.get(0));
		assertEquals(x, v4.get(0));
		
		assertEquals(y, v2.get(1));
		assertEquals(y, v3.get(1));
		assertEquals(y, v4.get(1));
		
		assertEquals(z, v3.get(2));
		assertEquals(z, v4.get(2));
		
		assertEquals(w, v4.get(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> v2.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> v3.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> v4.get(4));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> new VecNf().get(0));
	}
	
	@Test
	public void toArray()
	{
		final float	x	= randNum();
		final float	y	= randNum();
		final float	z	= randNum();
		final float	w	= randNum();
		
		final var	v0	= new VecNf();
		final var	v2	= new Vec2f(x, y);
		final var	v3	= new Vec3f(x, y, z);
		final var	v4	= new Vec4f(x, y, z, w);
		final var	vN	= new VecNf(x, y, z, w);
		
		assertArrayEquals(new float[] {}, v0.toArray());
		assertArrayEquals(new float[] { x, y }, v2.toArray());
		assertArrayEquals(new float[] { x, y, z }, v3.toArray());
		assertArrayEquals(new float[] { x, y, z, w }, v4.toArray());
		assertArrayEquals(new float[] { x, y, z, w }, vN.toArray());
	}
	
	@Test
	public void dotProduct()
	{
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		float		dp2	= 0;
		for (int i : range(f2.dimensions()))
			dp2 += f2.get(i) * s2.get(i);
		assertEquals(dp2, f2.dotProduct(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		float		dp3	= 0;
		for (int i : range(f3.dimensions()))
			dp3 += f3.get(i) * s3.get(i);
		assertEquals(dp3, f3.dotProduct(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		float		dp4	= 0;
		for (int i : range(f4.dimensions()))
			dp4 += f4.get(i) * s4.get(i);
		assertEquals(dp4, f4.dotProduct(s4));
	}
	
	@Test
	public void length()
	{
		final var	v2		= randVec2();
		float		len2	= 0;
		for (int i : range(v2.dimensions()))
			len2 += v2.get(i) * v2.get(i);
		assertEquals(len2, v2.lengthSQ());
		assertEquals(Math.sqrt(len2), v2.length());
		
		final var	v3		= randVec3();
		float		len3	= 0;
		for (int i : range(v3.dimensions()))
			len3 += v3.get(i) * v3.get(i);
		assertEquals(len3, v3.lengthSQ());
		assertEquals(Math.sqrt(len3), v3.length());
		
		final var	v4		= randVec4();
		float		len4	= 0;
		for (int i : range(v4.dimensions()))
			len4 += v4.get(i) * v4.get(i);
		assertEquals(len4, v4.lengthSQ());
		assertEquals(Math.sqrt(len4), v4.length());
	}
	
	@TestFactory
	Stream<DynamicTest> addN()
	{
		final FloatBinaryOperator op = (a, b) -> a + b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNf[%d].add".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNf.class, n);
				final var second = genVec(_i -> randNum(), VecNf.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNf.class, n);
				
				assertEquals(expected, first.add(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> subN()
	{
		final FloatBinaryOperator op = (a, b) -> a - b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNf[%d].sub".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNf.class, n);
				final var second = genVec(_i -> randNum(), VecNf.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNf.class, n);
				
				assertEquals(expected, first.sub(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> getN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNf[%d].get".formatted(n), () ->
			{
				final var values = new float[n];
				for (int i : range(n))
					values[i] = randNum();
				final var vec = new VecNf(values);
				for (int i : range(n))
					assertEquals(values[i], vec.get(i));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> dotProductN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNf[%d]·".formatted(n), () ->
			{
				final var vf = randArray(n);
				final var vs = randArray(n);
				float	dp	= 0;
				for (int i : range(n))
					dp += vf[i] * vs[i];
				assertEquals(dp, new VecNf(vf).dotProduct(new VecNf(vs)));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> lengthN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("‖VecNd[%d]‖".formatted(n), () ->
			{
				final var v	= randArray(n);
				float	len	= 0;
				for (int i : range(n))
					len += v[i] * v[i];
				assertEquals(len, new VecNf(v).lengthSQ());
				assertEquals(Math.sqrt(len), new VecNf(v).length());
			}));
	}
	
	private float[] randArray(int dimensions)
	{
		final var result = new float[dimensions];
		for (int i : range(dimensions))
			result[i] = randNum();
		return result;
	}
	
	private float randNum()
	{
		return rand.nextFloat(Float.MIN_VALUE / 2, Float.MAX_VALUE / 2);
	}
	
	private Vec2f randVec2()
	{
		return new Vec2f(randNum(), randNum());
	}
	
	private Vec3f randVec3()
	{
		return new Vec3f(randNum(), randNum(), randNum());
	}
	
	private Vec4f randVec4()
	{
		return new Vec4f(randNum(), randNum(), randNum(), randNum());
	}
}