package test.kryptonbutterfly.math;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public final class TestSettings
{
	private static final Logger log = LoggerFactory.getLogger(TestSettings.class);
	
	private static final String MAX_RETRIES_PROPERTY = "kryptonbutterfly-linear-test.max-retries";
	
	private static final TestSettings INSTANCE = new TestSettings();
	
	private final int MAX_RETRIES;
	
	public static int MAX_RETRIES()
	{
		return INSTANCE.MAX_RETRIES;
	}
	
	private TestSettings()
	{
		this.MAX_RETRIES = evalMaxRetries();
	}
	
	private static int evalMaxRetries()
	{
		final int	MAX_MAX_RETRIES		= 10_000;
		final int	MIN_MAX_RETRIES		= 1;
		final int	DEFAULT_MAX_RETRIES	= 100;
		
		final var maxRetriesStr = System.getProperty(MAX_RETRIES_PROPERTY);
		if (maxRetriesStr == null)
			return DEFAULT_MAX_RETRIES;
		
		final int maxRetries;
		try
		{
			maxRetries = Integer.parseInt(maxRetriesStr);
		}
		catch (NumberFormatException e)
		{
			log.warn(
				e,
				() -> "The property '%s' is not a valid integer! Falling back to %d.%n".formatted(
					MAX_RETRIES_PROPERTY,
					DEFAULT_MAX_RETRIES));
			return DEFAULT_MAX_RETRIES;
		}
		
		if (maxRetries < MIN_MAX_RETRIES)
		{
			log.warn(
				() -> "The property '%s' was set to %d but the minimmum value is %d! Falling back to %d.%n"
					.formatted(
						MAX_RETRIES_PROPERTY,
						maxRetries,
						MIN_MAX_RETRIES,
						MIN_MAX_RETRIES));
			return MIN_MAX_RETRIES;
		}
		
		if (maxRetries > MAX_MAX_RETRIES)
			log.info(
				() -> "The property '%s' was set to %d, but the maximum advisable value is %d.%n".formatted(
					MAX_RETRIES_PROPERTY,
					maxRetries,
					MAX_MAX_RETRIES));
		
		return maxRetries;
	}
}
