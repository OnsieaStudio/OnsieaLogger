package fr.onsiea.logger.tag;

import fr.onsiea.logger.EnumSeverity;
import fr.onsiea.logger.utils.DateUtils;
import fr.onsiea.logger.utils.TimeUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class TagParser
{
	private final static TagFunctions tagFunctions = new TagFunctions();
	private final static boolean ENCAPSULATION = false;
	private final static boolean HIGH_SEVERITY = true;
	private final static boolean LOG_WARN = true;
	private static Pattern PATTERN_OPEN = Pattern.compile("<");
	private static Pattern PATTERN_CLOSE = Pattern.compile(">");

	static
	{
		TagParser.tagFunctions.add("severity", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
				 {
					 if (EnumSeverity.NONE.equals(severityIn))
					 {
						 return "";
					 }
					 return severityIn.name();
				 }).add("severity_alias", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
				 {
					 if (EnumSeverity.NONE.equals(severityIn))
					 {
						 return "";
					 }
					 return severityIn.alias();
				 }).add("content", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> contentIn)
							  .add("pattern",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> patternIn)
							  .add("time", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
							  {
								  if (!tagInfoIn.parameters().isEmpty())
								  {
									  final var parameter = tagInfoIn.parameters().get(0);

									  if (parameter != null)
									  {
										  return TimeUtils.str(parameter);
									  }
								  }

								  return TimeUtils.str();
							  }).add("date", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
				 {
					 if (!tagInfoIn.parameters().isEmpty())
					 {
						 final var parameter = tagInfoIn.parameters().get(0);

						 if (parameter != null && parameter.matches("[GyMdkHmsSEDFwWahKzZYuXL\\._']"))
						 {
							 return DateUtils.str(parameter);
						 }
					 }

					 return DateUtils.str();
				 }).add("thread",
						(severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> Thread.currentThread().getName())
							  .add("classFullname",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
										   tagInfoIn.parameters(), currentStackTraceIn).getClassName())
							  .add("className", (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
							  {
								  final var className = TagParser.index(tagInfoIn.parameters(), currentStackTraceIn)
																 .getClassName();
								  return className.substring(className.lastIndexOf(".") + 1);
							  }).add("methodName",
									 (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
											 tagInfoIn.parameters(), currentStackTraceIn).getMethodName())
							  .add("fileName",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
										   tagInfoIn.parameters(), currentStackTraceIn).getFileName()).add("moduleName",
																										   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
																																																  tagInfoIn.parameters(),
																																																  currentStackTraceIn)
																																														  .getModuleName())
							  .add("moduleVersion",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
										   tagInfoIn.parameters(), currentStackTraceIn).getModuleVersion())
							  .add("classLoaderFullname",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> TagParser.index(
										   tagInfoIn.parameters(), currentStackTraceIn).getClassLoaderName())
							  .add("classLoaderName",
								   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
								   {
									   final var classLoaderName = TagParser.index(tagInfoIn.parameters(),
																				   currentStackTraceIn)
																			.getClassLoaderName();
									   return classLoaderName.substring(classLoaderName.lastIndexOf(".") + 1);
								   }).add("isNativeMethod",
										  (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) ->
												  TagParser.index(tagInfoIn.parameters(), currentStackTraceIn)
														   .isNativeMethod()
														  ? "nativeMethod"
														  : "notNativeMethod").add("lineNumber",
																				   (severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn) -> (
																						   "" + TagParser.index(
																												 tagInfoIn.parameters(),
																												 currentStackTraceIn)
																										 .getLineNumber()));
	}

	private StringBuilder stringBuilder;

	private String pattern;

	public TagParser()
	{
		this.stringBuilder(new StringBuilder());

		this.pattern("<content>");
	}

	public TagParser(String patternIn)
	{
		this.stringBuilder(new StringBuilder());

		this.pattern(patternIn);
	}

	private static StackTraceElement index(List<String> parametersIn, StackTraceElement currentStackTraceIn)
	{
		final var lastStackTrace = Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length
				- 4];

		if (!parametersIn.isEmpty())
		{
			final var value = parametersIn.get(0);

			switch (value)
			{
				case "last" ->
				{
					return lastStackTrace;
				}
				case "current" ->
				{
					return currentStackTraceIn;
				}
				default ->
				{
					final var i = Integer.parseInt(value);
					if (i >= 0 && i < Thread.currentThread().getStackTrace().length)
					{
						return Thread.currentThread().getStackTrace()[i];
					}
				}
			}
		}

		return currentStackTraceIn;
	}

	public static TagParsingResult parseAndReplace(EnumSeverity severityIn, String patternIn, String contentIn,
												   StackTraceElement currentStackTraceIn) throws Exception
	{
		var matcher = TagParser.PATTERN_OPEN.matcher(patternIn);
		final var OPENS = matcher.results().count();

		matcher = TagParser.PATTERN_CLOSE.matcher(patternIn);
		final var CLOSES = matcher.results().count();

		if (OPENS != CLOSES)
		{
			if (TagParser.HIGH_SEVERITY)
			{
				final var err = TagParser.err("[ERROR] TagParser Logs error : need as many opening and closing tags !",
											  patternIn);

				throw new Exception(err);
			}
			if (TagParser.LOG_WARN)
			{
				final var err = TagParser.err(
						"[WARNING] TagParser Logs error : need as many opening and closing tags !", patternIn);

				System.err.println(err);
			}
		}

		var disabled = false;
		var parameter = false;
		var level = 0;
		final Map<Integer, TagInfo> currents = new HashMap<>();
		TagInfo current = null;
		var stringBuilder = new StringBuilder();
		final var tagParsingResults = new TagParsingResult(severityIn, patternIn, contentIn, stringBuilder);

		for (var i = 0; i < patternIn.length(); i++)
		{
			final var c = patternIn.charAt(i);
			if (!disabled)
			{
				if (c == '<')
				{
					if (level > 0 && !TagParser.ENCAPSULATION)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err(
									"[ERROR] TagParser Logs error : no tag can be contained in another !", patternIn, i,
									patternIn.indexOf(">", i));

							throw new Exception(err);
						}
						if (TagParser.LOG_WARN)
						{
							final var err = TagParser.err(
									"[WARNING] TagParser Logs error : no tag can be contained in another !", patternIn,
									i, patternIn.indexOf(">", i));

							System.err.println(err);
						}
						disabled = true;
					}
					else
					{
						if (level > 0)
						{
							tagParsingResults.hasEncapsuled(true);
						}

						level++;

						current = new TagInfo(level > 0, level, i);
						current.content(current.content() + "<");
						currents.put(level, current);

						tagParsingResults.increaseLevels();
					}
				}
				else if (c == '>')
				{
					if (level < 0)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err(
									"[ERROR] TagParser Logs error : need as many opening and closing tags !", patternIn,
									i, i);

							throw new Exception(err);
						}
						if (TagParser.LOG_WARN)
						{
							final var err = TagParser.err(
									"[WARNING] TagParser Logs error : need as many opening and closing tags !",
									patternIn, i, i);

							System.err.println(err);
						}
					}

					final var tagInfo = currents.get(level);

					if (tagInfo == null)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err(
									"[ERROR] TagParser Logs error : compilation failed, tag at \"" + level
											+ "\" level is not defined !", patternIn, i, i);

							throw new Exception(err);
						}
						final var err = TagParser.err(
								"[WARNING] TagParser Logs error : compilation failed, tag at \"" + level
										+ "\" level is not defined !", patternIn, i, i);

						System.err.println(err);
					}
					else
					{
						currents.remove(level);
						tagParsingResults.add(tagInfo);

						tagInfo.end(i);

						tagInfo.content(tagInfo.content() + ">");
						final var name = tagInfo.content().replace("<", "").replace(">", "");
						final var elements = name.split(":");

						if (elements.length == 0)
						{
							tagInfo.name(name);
						}
						else
						{
							tagInfo.name(elements[0]);

							if (elements.length >= 2)
							{
								tagInfo.parameters(elements, 1, elements.length);
							}
						}

						final var replacement = TagParser.replacement(severityIn, patternIn, contentIn, tagInfo,
																	  currentStackTraceIn);

						if (replacement != null)
						{
							stringBuilder.append(replacement);
						}
						else
						{
							stringBuilder.append(tagInfo.content());
						}
					}
					level--;
				}
				else
				{
					if (c == ':')
					{
						parameter = true;
						if (current != null)
						{
							current.content(current.content() + c);
						}
					}
					else if (("" + c).matches("[a-zA-Z0-9$_]") || c == '.' || parameter)
					{
						if (current != null)
						{
							current.content(current.content() + c);
						}
					}

					if (level <= 0)
					{
						stringBuilder.append(c);
					}
				}
			}
			else if (c == '>')
			{
				disabled = false;
			}
		}

		return tagParsingResults;
	}

	private static String replacement(EnumSeverity severityIn, String patternIn, String contentIn, TagInfo tagInfoIn,
									  StackTraceElement currentStackTraceIn)
	{
		return TagParser.tagFunctions.replace(severityIn, patternIn, contentIn, tagInfoIn, currentStackTraceIn);
	}

	private static String err(String errorIn, String contentIn)
	{

		return errorIn + " [" + 0 + "  - " + contentIn.length() + "]  (" + contentIn.length() + ")" + "\n\"" + contentIn
				+ "\"";
	}

	private static String err(String errorIn, String contentIn, int startIndexIn, int endIndexIn)
	{

		return errorIn + " [" + startIndexIn + "  - " + endIndexIn + "] " + "]  (" + contentIn.length() + ")" + "\n\""
				+ contentIn + "\"";
	}

	public TagParser withPattern(String patterIn)
	{
		this.pattern(patterIn);

		return this;
	}

	public TagParsingResult parseAndReplace(EnumSeverity severityIn, String contentIn,
											StackTraceElement currentStackTraceIn) throws Exception
	{
		return parseAndReplace(severityIn, this.pattern, contentIn, currentStackTraceIn);
	}

	@EqualsAndHashCode
	@ToString
	@Getter
	@Setter(AccessLevel.PRIVATE)
	public final static class TagParsingResult
	{
		private EnumSeverity severity;
		private String pattern;
		private String content;
		private StringBuilder stringBuilder;
		private List<TagInfo> tags;
		private int levels;
		private boolean hasEncapsuled;

		public TagParsingResult(EnumSeverity severityIn, String patternIn, String contentIn,
								StringBuilder stringBuilderIn)
		{
			this.severity(severityIn);
			this.pattern(patternIn);
			this.content(contentIn);
			this.stringBuilder(stringBuilderIn);

			this.tags(new ArrayList<>());
		}

		public TagParsingResult add(TagInfo tagInfoIn)
		{
			this.tags().add(tagInfoIn);

			return this;
		}

		public int increaseLevels()
		{
			return this.levels++;
		}
	}
}