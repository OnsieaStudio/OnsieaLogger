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
	ILogger log(Object... objectsIn);

	ILogger logLn(Object... objectsIn);
}