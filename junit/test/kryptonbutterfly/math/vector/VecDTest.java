package test.kryptonbutterfly.math.vector;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import kryptonbutterfly.math.vector._double.IVecD;
import kryptonbutterfly.math.vector._double.Vec2d;
import kryptonbutterfly.math.vector._double.Vec3d;
import kryptonbutterfly.math.vector._double.Vec4d;
import kryptonbutterfly.math.vector._double.VecNd;
import lombok.SneakyThrows;
import test.kryptonbutterfly.math.Targets;

public class VecDTest
{
	private final Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <Vec extends IVecD<Vec>> Vec genVec(IntToDoubleFunction op, Class<Vec> t, int dims)
	{
		final var constructor = t.getConstructors()[0];
		if (constructor.isVarArgs())
		{
			final var values = new double[dims];
			for (int i : range(dims))
				values[i] = op.applyAsDouble(i);
			return (Vec) constructor.newInstance(values);
		}
		final var values = new Object[dims];
		for (int i : range(dims))
			values[i] = op.applyAsDouble(i);
		return (Vec) constructor.newInstance(values);
	}
	
	private static <Vec extends IVecD<Vec>> IntToDoubleFunction perform(Vec first, Vec second, DoubleBinaryOperator op)
	{
		return i -> op.applyAsDouble(first.get(i), second.get(i));
	}
	
	@Test
	public void add()
	{
		final DoubleBinaryOperator op = (a, b) -> a + b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2d.class, 2);
		assertEquals(e2, f2.add(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3d.class, 3);
		assertEquals(e3, f3.add(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		final var	e4	= genVec(perform(f4, s4, op), Vec4d.class, 4);
		assertEquals(e4, f4.add(s4));
	}
	
	@Test
	public void sub()
	{
		final DoubleBinaryOperator op = (a, b) -> a - b;
		
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		final var	e2	= genVec(perform(f2, s2, op), Vec2d.class, 2);
		assertEquals(e2, f2.sub(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		final var	e3	= genVec(perform(f3, s3, op), Vec3d.class, 3);
		assertEquals(e3, f3.sub(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		final var	e4	= genVec(perform(f4, s4, op), Vec4d.class, 4);
		assertEquals(e4, f4.sub(s4));
	}
	
	@Test
	public void get()
	{
		final double	x	= randNum();
		final double	y	= randNum();
		final double	z	= randNum();
		final double	w	= randNum();
		
		final var	v2	= new Vec2d(x, y);
		final var	v3	= new Vec3d(x, y, z);
		final var	v4	= new Vec4d(x, y, z, w);
		
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
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> new VecNd().get(0));
	}
	
	@Test
	public void toArray()
	{
		final double	x	= randNum();
		final double	y	= randNum();
		final double	z	= randNum();
		final double	w	= randNum();
		final double	v	= randNum();
		
		final var	v0	= new VecNd();
		final var	v2	= new Vec2d(x, y);
		final var	v3	= new Vec3d(x, y, z);
		final var	v4	= new Vec4d(x, y, z, w);
		final var	v5	= new VecNd(x, y, z, w, v);
		
		assertArrayEquals(new double[] {}, v0.toArray());
		assertArrayEquals(new double[] { x, y }, v2.toArray());
		assertArrayEquals(new double[] { x, y, z }, v3.toArray());
		assertArrayEquals(new double[] { x, y, z, w }, v4.toArray());
		assertArrayEquals(new double[] { x, y, z, w, v }, v5.toArray());
	}
	
	@Test
	public void dotProduct()
	{
		final var	f2	= randVec2();
		final var	s2	= randVec2();
		double		dp2	= 0;
		for (int i : range(f2.dimensions()))
			dp2 += f2.get(i) * s2.get(i);
		assertEquals(dp2, f2.dotProduct(s2));
		
		final var	f3	= randVec3();
		final var	s3	= randVec3();
		double		dp3	= 0;
		for (int i : range(f3.dimensions()))
			dp3 += f3.get(i) * s3.get(i);
		assertEquals(dp3, f3.dotProduct(s3));
		
		final var	f4	= randVec4();
		final var	s4	= randVec4();
		double		dp4	= 0;
		for (int i : range(f4.dimensions()))
			dp4 += f4.get(i) * s4.get(i);
		assertEquals(dp4, f4.dotProduct(s4));
	}
	
	@Test
	public void length()
	{
		final var	v2		= randVec2();
		double		len2	= 0;
		for (int i : range(v2.dimensions()))
			len2 += v2.get(i) * v2.get(i);
		assertEquals(len2, v2.lengthSQ());
		assertEquals(Math.sqrt(len2), v2.length());
		
		final var	v3		= randVec3();
		double		len3	= 0;
		for (int i : range(v3.dimensions()))
			len3 += v3.get(i) * v3.get(i);
		assertEquals(len3, v3.lengthSQ());
		assertEquals(Math.sqrt(len3), v3.length());
		
		final var	v4		= randVec4();
		double		len4	= 0;
		for (int i : range(v4.dimensions()))
			len4 += v4.get(i) * v4.get(i);
		assertEquals(len4, v4.lengthSQ());
		assertEquals(Math.sqrt(len4), v4.length());
	}
	
	@TestFactory
	Stream<DynamicTest> addN()
	{
		final DoubleBinaryOperator op = (a, b) -> a + b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNd[%d].add".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNd.class, n);
				final var second = genVec(_i -> randNum(), VecNd.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNd.class, n);
				
				assertEquals(expected, first.add(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> subN()
	{
		final DoubleBinaryOperator op = (a, b) -> a - b;
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNd[%d].sub".formatted(n), () ->
			{
				final var first	= genVec(_i -> randNum(), VecNd.class, n);
				final var second = genVec(_i -> randNum(), VecNd.class, n);
				
				final var expected = genVec(perform(first, second, op), VecNd.class, n);
				
				assertEquals(expected, first.sub(second));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> getN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNd[%d].get".formatted(n), () ->
			{
				final var values = randArray(n);
				final var vec	= new VecNd(values);
				for (int i : range(n))
					assertEquals(values[i], vec.get(i));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> dotProductN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("VecNd[%d]·".formatted(n), () ->
			{
				final var vf = randArray(n);
				final var vs = randArray(n);
				double	dp	= 0;
				for (int i : range(n))
					dp += vf[i] * vs[i];
				assertEquals(dp, new VecNd(vf).dotProduct(new VecNd(vs)));
			}));
	}
	
	@TestFactory
	Stream<DynamicTest> lengthN()
	{
		return Targets.createDimsStream()
			.mapToObj(n -> DynamicTest.dynamicTest("‖VecNd[%d]‖".formatted(n), () ->
			{
				final var v	= randArray(n);
				double	len	= 0;
				for (int i : range(n))
					len += v[i] * v[i];
				assertEquals(len, new VecNd(v).lengthSQ());
				assertEquals(Math.sqrt(len), new VecNd(v).length());
			}));
	}
	
	private double[] randArray(int dimensions)
	{
		final var result = new double[dimensions];
		for (int i : range(dimensions))
			result[i] = randNum();
		return result;
	}
	
	private double randNum()
	{
		return rand.nextDouble(Double.MIN_VALUE / 2, Double.MAX_VALUE / 2);
	}
	
	private Vec2d randVec2()
	{
		return new Vec2d(randNum(), randNum());
	}
	
	private Vec3d randVec3()
	{
		return new Vec3d(randNum(), randNum(), randNum());
	}
	
	private Vec4d randVec4()
	{
		return new Vec4d(randNum(), randNum(), randNum(), randNum());
	}
}