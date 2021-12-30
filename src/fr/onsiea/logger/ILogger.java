/**
 *
 */
package fr.onsiea.logger;

/**
 * @author Seynax
 *
 */
public interface ILogger
{
	ILogger log(EnumSeverity severityIn, Object... objectsIn);

	ILogger logLn(EnumSeverity severityIn, Object... objectsIn);

	ILogger log(Object... objectsIn);

	ILogger logLn(Object... objectsIn);
}