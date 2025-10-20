package test.kryptonbutterfly.math.matrix;

import static kryptonbutterfly.math.utils.range.Range.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import kryptonbutterfly.functions.int_.BiIntToDoubleFunction;
import kryptonbutterfly.math.AlgebraSettings;
import kryptonbutterfly.math.matrix.AMatrix;
import kryptonbutterfly.math.matrix._double.AMatrixD;
import kryptonbutterfly.math.matrix._double.MatrixD;
import kryptonbutterfly.math.matrix._double.MatrixD4x4;
import kryptonbutterfly.math.vector._double.IVecD;
import kryptonbutterfly.math.vector._double.Vec4d;
import kryptonbutterfly.math.vector._double.VecNd;
import lombok.SneakyThrows;
import test.kryptonbutterfly.math.Targets;

public class MatDTest
{
	private final Random rand = new Random();
	
	@BeforeAll
	public static void init()
	{
		AlgebraSettings.settings.FP_STRINGIFY_TEMPLATE = " %-7.2f";
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <Vec extends IVecD<Vec>, Mat extends AMatrix<Vec, Mat, double[]>> Vec genVec(
		IntToDoubleFunction op,
		Class<Vec> t,
		Mat mat)
	{
		final var constructor = t.getConstructors()[0];
		if (constructor.isVarArgs())
		{
			final var values = new double[mat.height];
			for (int y : range(values.length))
				values[y] = op.applyAsDouble(y);
			return (Vec) constructor.newInstance(values);
		}
		final var values = new Object[mat.height];
		for (int y : range(values.length))
			values[y] = op.applyAsDouble(y);
		return (Vec) constructor.newInstance(values);
	}
	
	@SneakyThrows
	private static <Vec extends IVecD<Vec>, Mat extends AMatrix<Vec, Mat, double[]>> Mat genMat(
		BiIntToDoubleFunction op,
		Class<Mat> t,
		Mat f,
		Mat s)
	{
		final var	constructor	= t.getConstructor(double[].class);
		final var	values		= new double[f.height * s.width];
		for (final int y : range(f.height))
			for (final int x : range(s.width))
				values[y * s.width + x] = op.apply(x, y);
		return (Mat) constructor.newInstance(values);
	}
	
	@SneakyThrows
	private static <Vec extends IVecD<Vec>, Mat extends AMatrixD<Vec, Mat>> Mat genMatN(
		BiIntToDoubleFunction op,
		Class<Mat> t,
		Mat f,
		Mat s)
	{
		final var	constructor	= t.getConstructor(int.class, int.class, double[].class);
		final var	values		= new double[f.height * s.width];
		for (final int y : range(f.height))
			for (final int x : range(s.width))
				values[y * s.width + x] = op.apply(x, y);
		return (Mat) constructor.newInstance(s.width, f.height, values);
	}
	
	@Test
	public void mulVec()
	{
		final var	f0	= new Vec4d(1, 2, 3, 4);
		final var	m0	= new MatrixD4x4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		final var	e0	= genVec(perform(m0, f0), Vec4d.class, m0);
		assertEquals(e0, m0.mul(f0));
		
		final var	f1	= randVec4();
		final var	m1	= randMat4x4();
		final var	e1	= genVec(perform(m1, f1), Vec4d.class, m1);
		assertEquals(e1, m1.mul(f1));
	}
	
	private static <Vec extends IVecD<Vec>, Mat extends AMatrixD<Vec, Mat>> IntToDoubleFunction perform(
		Mat mat,
		Vec vec)
	{
		return y -> {
			double sum = 0;
			for (int x : range(mat.width))
				sum += vec.get(x) * mat.getBackingArray()[y * mat.width + x];
			return sum;
		};
	}
	
	@Test
	public void mulMat()
	{
		final var	f4	= randMat4x4();
		final var	s4	= randMat4x4();
		
		final var e4 = genMat((x, y) -> {
			double sum = 0;
			for (int i : range(f4.width))
				sum += f4.getBackingArray()[y * f4.width + i]
					* s4.getBackingArray()[i * s4.width + x];
			return sum;
		}, MatrixD4x4.class, f4, s4);
		
		final var actual = f4.mul(s4);
		
		assertEquals(e4, actual);
	}
	
	@Test
	public void transpose()
	{
		final var	m0	= randMat4x4();
		final var	t0	= m0.transpose();
		assertEquals(m0.width, t0.height);
		assertEquals(m0.height, t0.width);
		assertEquals(m0, t0.transpose());
	}
	
	@TestFactory
	Stream<DynamicTest> mulVecN()
	{
		return Targets.createDimsStream2().map(dims -> {
			return DynamicTest.dynamicTest("MatNd[%d, %d].mulVecN".formatted(dims.x(), dims.y()), () -> {
				final var	f	= randVec(dims.x());
				final var	m	= randMat(dims.x(), dims.y());
				final var	e	= genVec(perform(m, f), VecNd.class, m);
				assertEquals(e, m.mul(f));
			});
		});
	}
	
	@TestFactory
	Stream<DynamicTest> mulMatN()
	{
		return Targets.createDimsStream3().map(dims -> {
			return DynamicTest.dynamicTest("Mat.mulMat(%d, %d, %d)".formatted(dims.x(), dims.y(), dims.z()), () -> {
				final var	f	= randMat(dims.z(), dims.y());
				final var	s	= randMat(dims.x(), dims.z());
				
				final var e = genMatN((x, y) -> {
					double sum = 0;
					for (int i : range(f.width))
						sum += f.getBackingArray()[y * f.width + i]
							* s.getBackingArray()[i * s.width + x];
					return sum;
				}, MatrixD.class, f, s);
				
				assertEquals(e, f.mul(s));
			});
		});
	}
	
	@TestFactory
	Stream<DynamicTest> transposeN()
	{
		return Targets.createDimsStream2().map(dims -> {
			return DynamicTest.dynamicTest("MatNd[%d, %d].transposeN".formatted(dims.x(), dims.y()), () -> {
				final var	m	= randMat(dims.x(), dims.y());
				final var	t	= m.transpose();
				assertEquals(m.width, t.height);
				assertEquals(m.height, t.width);
				assertEquals(m, t.transpose());
			});
		});
	}
	
	private double[] randArray(int dimensions)
	{
		final var result = new double[dimensions];
		for (int i : range(dimensions))
			result[i] = randNum();
		return result;
	}
	
	private double randNum10()
	{
		return rand.nextDouble(-10, 10);
	}
	
	private double randNumLarge()
	{
		return rand.nextDouble(
			Math.sqrt(Double.MIN_VALUE) / 0x1000_0000_0000_0000L,
			Math.sqrt(Double.MAX_VALUE) / 0x1000_0000_0000_0000L);
	}
	
	private double randNum()
	{
		return randNum10();
	}
	
	private Vec4d randVec4()
	{
		return new Vec4d(randNum(), randNum(), randNum(), randNum());
	}
	
	private VecNd randVec(int dims)
	{
		final var data = new double[dims];
		for (final int i : range(dims))
			data[i] = randNum();
		return new VecNd(data);
	}
	
	private MatrixD4x4 randMat4x4()
	{
		return new MatrixD4x4(randArray(16));
	}
	
	private MatrixD randMat(int width, int height)
	{
		return new MatrixD(width, height, randArray(width * height));
	}
}
