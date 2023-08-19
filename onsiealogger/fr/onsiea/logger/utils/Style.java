/**
 *
 */
package fr.onsiea.logger.utils;

/**
 * @author Seynax
 */
public class Style
{
	public final static String BASE = "\u001b[1;";
	public final static String RESET = "\u001b[1;0m";
	public final static String BOLD = "\u001b[1;1m";
	public final static String INTENSITY = "\u001b[1;2m";
	public final static String ITALIC = "\u001b[1;3m";
	public final static String UNDERLINE = "\u001b[1;4m";
	public final static String NEGATIVE = "\u001b[1;7m";
	public final static String CONCEAL = "\u001b[1;8m";
	public final static String CROSSED_OUT = "\u001b[1;9m";
	public final static String DOUBLE_UNDERLINE = "\u001b[1;21m";
	public final static String BOLD_OFF = "\u001b[1;22m";
	public final static String ITALIC_OFF = "\u001b[1;23m";
	public final static String UNDERLINE_OFF = "\u001b[1;24m";
	public final static String NEGATIVE_OFF = "\u001b[1;27m";
	public final static String CONCEAL_OFF = "\u001b[1;28m";
	public final static String CROSSED_OUT_OFF = "\u001b[1;29m";
	public final static String FRAMED = "\u001b[1;51m";
	public final static String FRAMED_OFF = "\u001b[1;54m";

	public static class Color
	{
		public final static String BLACK = "\u001b[1;30m";
		public final static String RED = "\u001b[1;31m";
		public final static String GREEN = "\u001b[1;32m";
		public final static String YELLOW = "\u001b[1;33m";
		public final static String BLUE = "\u001b[1;34m";
		public final static String MAGENTA = "\u001b[1;35m";
		public final static String CYAN = "\u001b[1;36m";
		public final static String WHITE = "\u001b[1;37m";
		public final static String XTERM = "\u001b[1;38m";
		public final static String DEFAULT = "\u001b[1;39m";
	}

	public static class BackColor
	{
		public final static String BLACK = "\u001b[1;40m";
		public final static String RED = "\u001b[1;41m";
		public final static String GREEN = "\u001b[1;42m";
		public final static String YELLOW = "\u001b[1;43m";
		public final static String BLUE = "\u001b[1;44m";
		public final static String MAGENTA = "\u001b[1;45m";
		public final static String CYAN = "\u001b[1;46m";
		public final static String WHITE = "\u001b[1;47m";
		public final static String XTERM = "\u001b[1;48m";
		public final static String DEFAULT = "\u001b[1;49m";
	}

	public static class BackColorIntensity
	{
		public final static String BLACK = "\u001b[1;90m";
		public final static String RED = "\u001b[1;91m";
		public final static String GREEN = "\u001b[1;92m";
		public final static String YELLOW = "\u001b[1;93m";
		public final static String BLUE = "\u001b[1;94m";
		public final static String MAGENTA = "\u001b[1;95m";
		public final static String CYAN = "\u001b[1;96m";
		public final static String WHITE = "\u001b[1;97m";
		public final static String XTERM = "\u001b[1;98m";
		public final static String DEFAULT = "\u001b[1;99m";
	}

	public static class ForeColor
	{
		public final static String BLACK = "\u001b[1;100m";
		public final static String RED = "\u001b[1;101m";
		public final static String GREEN = "\u001b[1;102m";
		public final static String YELLOW = "\u001b[1;103m";
		public final static String BLUE = "\u001b[1;104m";
		public final static String MAGENTA = "\u001b[1;105m";
		public final static String CYAN = "\u001b[1;106m";
		public final static String WHITE = "\u001b[1;107m";
		public final static String XTERM = "\u001b[1;108m";
		public final static String DEFAULT = "\u001b[1;109m";
	}
}