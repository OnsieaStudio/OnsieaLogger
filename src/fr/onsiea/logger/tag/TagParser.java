/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of OnsieaLogger. (https://github.com/Onsiea/OnsieaLogger)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE_NOTICE.md), OnsieaLogger and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	OnsieaLogger is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	OnsieaLogger is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with OnsieaLogger.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "OnsieaLogger", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaLogger/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.logger.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fr.onsiea.logger.EnumSeverity;
import fr.onsiea.logger.utils.DateUtils;
import fr.onsiea.logger.utils.TimeUtils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class TagParser
{
	private final static boolean	ENCAPSULATION	= false;
	private final static boolean	HIGH_SEVERITY	= true;
	private final static boolean	LOG_WARN		= true;

	private static Pattern			PATTERN_OPEN	= Pattern.compile("<");
	private static Pattern			PATTERN_CLOSE	= Pattern.compile(">");
	// In progress
	//private static Pattern			PATTERN_TAG		= Pattern
	//		.compile("((<[[\\W\\w]&&[^<]]*>)([[\\w\\W]&&[^<>]]+|[[\\w\\W]&&[^<>]]*<|[[\\w\\W]&&[^<>]]*$))");

	public final static TagParsingResult parseAndReplace(StringBuilder stringBuilderIn, EnumSeverity severityIn,
			String contentIn) throws Exception
	{
		return TagParser.parseAndReplace(stringBuilderIn, severityIn, contentIn, "");
	}

	public final static TagParsingResult parseAndReplace(StringBuilder stringBuilderIn, EnumSeverity severityIn,
			String patternIn, String contentIn) throws Exception
	{
		var			matcher	= TagParser.PATTERN_OPEN.matcher(patternIn);
		final var	OPENS	= matcher.results().count();

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
				final var err = TagParser
						.err("[WARNING] TagParser Logs error : need as many opening and closing tags !", patternIn);

				System.err.println(err);
			}
		}

		var							disabled	= false;
		var							parameter	= false;
		var							level		= 0;
		final Map<Integer, TagInfo>	currents	= new HashMap<>();
		TagInfo						current		= null;
		stringBuilderIn = new StringBuilder();
		final var tagParsingResults = new TagParsingResult(severityIn, patternIn, contentIn, stringBuilderIn);

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
							final var err = TagParser.err("[ERROR] TagParser Logs error : compilation failed, tag at \""
									+ level + "\" level is not defined !", patternIn, i, i);

							throw new Exception(err);
						}
						final var err = TagParser.err("[WARNING] TagParser Logs error : compilation failed, tag at \""
								+ level + "\" level is not defined !", patternIn, i, i);

						System.err.println(err);
					}
					else
					{
						currents.remove(level);
						tagParsingResults.add(tagInfo);

						tagInfo.end(i);

						tagInfo.content(tagInfo.content() + ">");
						final var	name		= tagInfo.content().replace("<", "").replace(">", "");
						final var	elements	= name.split(":");

						if (elements.length <= 0)
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

						final var replacement = TagParser.replacement(severityIn, patternIn, contentIn, tagInfo);

						if (replacement != null)
						{
							stringBuilderIn.append(replacement);
						}
						else
						{
							stringBuilderIn.append(tagInfo.content());
						}
					}
					level--;
				}
				else
				{
					if (c == ':')
					{
						parameter = true;
						current.content(current.content() + c);
					}
					else if (("" + c).matches("[a-zA-Z0-9$_]") || c == '.' || parameter)
					{
						current.content(current.content() + c);
					}

					if (level <= 0)
					{
						stringBuilderIn.append(c);
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

	private final static String replacement(EnumSeverity severityIn, String patternIn, String contentIn,
			TagInfo tagInfoIn)
	{
		switch (tagInfoIn.name())
		{
			case "severity":
				if (EnumSeverity.NONE.equals(severityIn))
				{
					return null;
				}
				return severityIn.name();

			case "severity_alias":
				if (EnumSeverity.NONE.equals(severityIn))
				{
					return null;
				}
				return severityIn.alias();

			case "content":
				return contentIn;

			case "pattern":
				return patternIn;

			case "time":
				if (tagInfoIn.parameters().size() > 0)
				{
					final var parameter = tagInfoIn.parameters().get(0);

					if (parameter != null)
					{
						return TimeUtils.str(parameter);
					}
				}

				return TimeUtils.str();

			case "date":
				if (tagInfoIn.parameters().size() > 0)
				{
					final var parameter = tagInfoIn.parameters().get(0);

					if (parameter != null && parameter.matches("[GyMdkHmsSEDFwWahKzZYuXL\\._']"))
					{
						return DateUtils.str(parameter);
					}
				}

				return DateUtils.str();
		}

		return null;
	}

	private final static String err(String errorIn, String contentIn)
	{
		final var builder = new StringBuilder(
				errorIn + " [" + 0 + "  - " + contentIn.length() + "]  (" + contentIn.length() + ")");
		builder.append("\n\"" + contentIn + "\"");

		return builder.toString();
	}

	private final static String err(String errorIn, String contentIn, int startIndexIn, int endIndexIn)
	{
		final var builder = new StringBuilder(
				errorIn + " [" + startIndexIn + "  - " + endIndexIn + "] " + "]  (" + contentIn.length() + ")");
		builder.append("\n\"" + contentIn + "\"");

		return builder.toString();
	}

	private StringBuilder	stringBuilder;
	private String			pattern;

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

	public TagParser withPattern(String patterIn)
	{
		this.pattern(patterIn);

		return this;
	}

	public TagParsingResult parseAndReplace(EnumSeverity severityIn, String contentIn) throws Exception
	{
		return TagParser.parseAndReplace(this.stringBuilder(), severityIn, this.pattern(), contentIn);
	}

	public TagParsingResult parseAndReplace(EnumSeverity severityIn, String patternIn, String contentIn)
			throws Exception
	{
		return TagParser.parseAndReplace(this.stringBuilder(), severityIn, patternIn, contentIn);
	}

	@EqualsAndHashCode
	@ToString
	@Getter
	@Setter(AccessLevel.PRIVATE)
	public final static class TagParsingResult
	{
		private EnumSeverity	severity;
		private String			pattern;
		private String			content;
		private StringBuilder	stringBuilder;
		private List<TagInfo>	tags;
		private int				levels;
		private boolean			hasEncapsuled;

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