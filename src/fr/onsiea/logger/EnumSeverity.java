/**
 *
 */
package fr.onsiea.logger;

/**
 * @author Seynax
 *
 */
public enum EnumSeverity
{
	INFORMATION("INFO"), WARNING("WARN"), ERROR("ERR", true), CRITICAL("CRITIC", true);

	private String	alias;
	private boolean	errStream;

	EnumSeverity(String aliasIn)
	{
		this.alias(aliasIn);
	}

	EnumSeverity(String aliasIn, boolean errStreamIn)
	{
		this.alias(aliasIn);
		this.errStream(errStreamIn);
	}

	public final String alias()
	{
		return this.alias;
	}

	private final void alias(String aliasIn)
	{
		this.alias = aliasIn;
	}

	public final boolean errStream()
	{
		return this.errStream;
	}

	private final void errStream(boolean errStreamIn)
	{
		this.errStream = errStreamIn;
	}
}