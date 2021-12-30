/**
 *
 */
package fr.onsiea.logger;

import java.io.PrintStream;

/**
 * @author Seynax
 *
 */
public class LogUtils
{
	public final static String toString(Object... objectsIn)
	{
		final var content = new StringBuilder();

		for (final Object object : objectsIn)
		{
			content.append(object.toString());
		}

		return content.toString();
	}

	public final static void log(Object... objectsIn)
	{
		System.out.print(LogUtils.toString(objectsIn));
	}

	public final static void logLn(Object... objectsIn)
	{
		System.out.println(LogUtils.toString(objectsIn));
	}

	public final static void logErr(Object... objectsIn)
	{
		System.err.print(LogUtils.toString(objectsIn));
	}

	public final static void logErrLn(Object... objectsIn)
	{
		System.err.println(LogUtils.toString(objectsIn));
	}

	public final static void logIn(PrintStream printStreamIn, Object... objectsIn)
	{
		printStreamIn.print(LogUtils.toString(objectsIn));
	}

	public final static void logInLn(PrintStream printStreamIn, Object... objectsIn)
	{
		printStreamIn.println(LogUtils.toString(objectsIn));
	}
}