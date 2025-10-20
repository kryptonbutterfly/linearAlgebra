package kryptonbutterfly.math.matrix;

import static kryptonbutterfly.math.AlgebraSettings.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.IntFunction;

import kryptonbutterfly.math.vector.IVector;

public abstract class AMatrix<Vec extends IVector<Vec>, Mat extends AMatrix<Vec, Mat, T>, T>
{
	public final int				width, height;
	protected final T				data;
	protected final IntFunction<T>	arrayConstructor;
	
	protected AMatrix(int width, int height, T data, IntFunction<T> arrayConstructor)
	{
		assert width > 0 : "width must be > 0!";
		this.width = width;
		
		assert height > 0 : "height must be > 0!";
		this.height = height;
		
		assertSize(data);
		this.data = data;
		
		assert arrayConstructor != null : "'arrayConstructor' must not be null!";
		this.arrayConstructor = arrayConstructor;
	}
	
	protected abstract Mat create(int width, int height, T data);
	
	/**
	 * This method multiplies the value at {@code thisIndex} with the value at
	 * {@code right[rightIndex]} and adds the result to {@code target[targetIndex]}.
	 * </br>
	 * </br>
	 * {@code target[targetIndex] += this.data[thisIndex] + right[rightIndex]; }
	 * 
	 * @param target
	 *            The target array to add the result to.
	 * @param targetIndex
	 *            The index where to add the result to the target array.
	 * @param thisIndex
	 *            The index in {@code this} backing array to multiply.
	 * @param right
	 *            The backing array of the right matrix.
	 * @param rightIndex
	 *            The index into the backing array of the right matrix.
	 */
	protected abstract void mulAdd(T target, int targetIndex, int thisIndex, T right, int rightIndex);
	
	public abstract Vec mul(Vec vec);
	
	public final Mat mul(Mat right)
	{
		final int	w	= right.width;
		final int	h	= this.height;
		
		final var	result	= arrayConstructor.apply(w * h);
		final var	m		= create(w, h, result);
		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
			{
				final int tI = j + i * w;
				for (int k = 0; k < right.height; k++)
				{
					final int	lI	= k + i * width;
					final int	rI	= j + k * w;
					mulAdd(result, tI, lI, right.data, rI);
				}
			}
		return m;
	}
	
	public final Mat transpose()
	{
		final var result = arrayConstructor.apply(arraySize());
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				Array.set(result, x * height + y, Array.get(data, x + y * width));
		return create(height, width, result);
	}
	
	private void assertSize(Object data)
	{
		final var length = Array.getLength(data);
		assert length == arraySize() : "expected 'data' of length %d, but was %d".formatted(
			arraySize(),
			length);
	}
	
	public final T getBackingArray()
	{
		return data;
	}
	
	public final int arraySize()
	{
		return width * height;
	}
	
	public final void assertVecDims(int expected, int actual)
	{
		assert expected == actual : "expected vec to have %d dimensions but has %d!"
			.formatted(expected, actual);
	}
	
	public boolean equals(Object second, double epsilon)
	{
		if (this == second)
			return true;
		if (second == null)
			return false;
		if (!(second instanceof AMatrix sec))
			return false;
		if (width != sec.width)
			return false;
		if (height != sec.height)
			return false;
		
		final var sb = sec.getBackingArray();
		
		final var backingType = data.getClass();
		if (backingType != sb.getClass())
			return false;
		
		if (backingType == float[].class)
			return floatArrayEquals(
				(float[]) data,
				(float[]) sb,
				(float) epsilon);
		
		if (backingType == double[].class)
			return doubleArrayEquals(
				(double[]) data,
				(double[]) sb,
				(double) epsilon);
		
		if (backingType == byte[].class)
			return Arrays.equals((byte[]) data, (byte[]) sb);
		
		if (backingType == short[].class)
			return Arrays.equals((short[]) data, (short[]) sb);
		
		if (backingType == int[].class)
			return Arrays.equals((int[]) data, (int[]) sb);
		
		if (backingType == long[].class)
			return Arrays.equals((long[]) data, (long[]) sb);
		
		throw new RuntimeException("".formatted(backingType));
	}
	
	private static boolean floatArrayEquals(float[] first, float[] second, float epsilon)
	{
		for (int i = 0; i < first.length; i++)
		{
			final float diff = first[i] - second[i];
			if (Math.abs(diff) > epsilon)
				return false;
		}
		return true;
	}
	
	private static boolean doubleArrayEquals(double[] first, double[] second, double epsilon)
	{
		for (int i = 0; i < first.length; i++)
		{
			final double diff = first[i] - second[i];
			if (Math.abs(diff) > epsilon)
				return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		final var sb = new StringBuilder().append("\n");
		return switch (height)
		{
			case 0 -> "";
			case 1 ->
			{
				sb.append('(');
				for (int i = 0; i < width; i++)
				{
					if (i > 0)
						sb.append(',');
					sb.append(settings.FP_STRINGIFY_TEMPLATE.formatted(Array.get(data, i)));
				}
				sb.append(')');
				yield sb.toString();
			}
			default ->
			{
				for (int y = 0; y < height; y++)
				{
					if (y == 0)
						sb.append('/');
					else if (y == height - 1)
						sb.append('\\');
					else
						sb.append('|');
					
					for (int x = 0; x < width; x++)
					{
						if (x > 0)
							sb.append(", ");
						sb.append(settings.FP_STRINGIFY_TEMPLATE.formatted(Array.get(data, y * width + x)));
					}
					
					if (y == 0)
						sb.append('\\');
					else if (y == height - 1)
						sb.append('/');
					else
						sb.append('|');
					sb.append('\n');
				}
				yield sb.toString();
			}
		};
	}
}
