/**
 *
 */
package fr.onsiea.tools.logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Seynax
 */
public class Loggers
{
	private @Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE) Map<String, ILogger> loggers;

	public Loggers()
	{
		this.loggers(new HashMap<>());
	}

	public static Loggers consoleAndFile(String outFilepathIn, String errFilepathIn) throws Exception
	{
		return new Loggers().put("console", new ConsoleLogger())
				.put("file", new FileLogger.Builder(outFilepathIn, errFilepathIn).build());
	}

	public static Loggers consoleAndFile(String outFilepathIn, String errFilepathIn, boolean appendInFileIn)
			throws Exception
	{
		return new Loggers().put("console", new ConsoleLogger())
				.put("file", new FileLogger.Builder(outFilepathIn, errFilepathIn).append(appendInFileIn).build());
	}

	public static Loggers consoleAndFile(String patternIn, String outFilepathIn, String errFilepathIn) throws Exception
	{
		return new Loggers().put("console", new ConsoleLogger(patternIn))
				.put("file", new FileLogger.Builder(outFilepathIn, errFilepathIn).pattern(patternIn).build());
	}

	public static Loggers consoleAndFile(String patternIn, String outFilepathIn, String errFilepathIn,
			boolean appendInFileIn) throws Exception
	{
		return new Loggers().put("console", new ConsoleLogger(patternIn)).put("file",
				new FileLogger.Builder(outFilepathIn, errFilepathIn).pattern(patternIn).append(appendInFileIn).build());
	}

	public Loggers put(String nameIn, ILogger loggerIn)
	{
		this.loggers().put(nameIn, loggerIn);

		return this;
	}

	public ILogger get(String nameIn)
	{
		return this.loggers().get(nameIn);
	}

	public Loggers withPattern(String patternIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.withPattern(patternIn);
		}

		return this;
	}

	public Loggers log(EnumSeverity severityIn, Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.log(severityIn, objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}

	public Loggers logLn(EnumSeverity severityIn, Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.logLn(severityIn, objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}

	public Loggers log(Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.log(objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}

	public Loggers logLn(Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.logLn(objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}

	public Loggers logErr(Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.logErr(objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}

	public Loggers logErrLn(Object... objectsIn)
	{
		for (final ILogger logger : this.loggers().values())
		{
			logger.increaseStackTraceIncrement();
			logger.logErrLn(objectsIn);
			logger.resetStackTraceIncrement();
		}

		return this;
	}
}