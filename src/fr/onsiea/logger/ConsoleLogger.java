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