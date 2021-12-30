/**
 *
 */
package fr.onsiea.logger;

/**
 * @author Seynax
 *
 */
public class ConsoleLogger implements ILogger
{
	@Override
	public ILogger log(EnumSeverity severityIn, Object... objectsIn)
	{
		final var content = "[" + severityIn.alias() + "]" + LogUtils.toString(objectsIn);

		if (severityIn.errStream())
		{
			System.err.print(content);
		}
		else
		{
			System.out.print(content);
		}

		return this;
	}

	@Override
	public ILogger logLn(EnumSeverity severityIn, Object... objectsIn)
	{
		final var content = "[" + severityIn.alias() + "]" + LogUtils.toString(objectsIn);
		if (severityIn.errStream())
		{
			System.err.println(content);
		}
		else
		{
			System.out.println(content);
		}

		return this;
	}

	@Override
	public ILogger log(Object... objectsIn)
	{
		System.out.print(LogUtils.toString(objectsIn));

		return this;
	}

	@Override
	public ILogger logLn(Object... objectsIn)
	{
		System.out.println(LogUtils.toString(objectsIn));

		return this;
	}
}