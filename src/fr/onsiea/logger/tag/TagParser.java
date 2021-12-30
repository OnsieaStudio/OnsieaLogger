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

/**
 * @author Seynax
 *
 */
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

	public final static List<TagInfo> parseAndReplace(String contentIn) throws Exception
	{
		var			matcher	= TagParser.PATTERN_OPEN.matcher(contentIn);
		final var	OPENS	= matcher.results().count();

		matcher = TagParser.PATTERN_CLOSE.matcher(contentIn);
		final var CLOSES = matcher.results().count();

		if (OPENS != CLOSES)
		{
			if (TagParser.HIGH_SEVERITY)
			{
				final var err = TagParser.err("[ERROR] TagParser Logs error : need as many opening and closing tags !",
						contentIn);

				throw new Exception(err);
			}
			if (TagParser.LOG_WARN)
			{
				final var err = TagParser
						.err("[WARNING] TagParser Logs error : need as many opening and closing tags !", contentIn);

				System.err.println(err);
			}
		}

		var							disabled	= true;
		var							level		= 0;
		final List<TagInfo>			tags		= new ArrayList<>();
		final Map<Integer, TagInfo>	currents	= new HashMap<>();
		TagInfo						current		= null;

		for (var i = 0; i < contentIn.length(); i++)
		{
			final var c = contentIn.charAt(i);
			if (!disabled)
			{
				if (c == '<')
				{
					if (level > 0 && !TagParser.ENCAPSULATION)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err(
									"[ERROR] TagParser Logs error : no tag can be contained in another !", contentIn, i,
									contentIn.indexOf(">", i));

							throw new Exception(err);
						}
						if (TagParser.LOG_WARN)
						{
							final var err = TagParser.err(
									"[WARNING] TagParser Logs error : no tag can be contained in another !", contentIn,
									i, contentIn.indexOf(">", i));

							System.err.println(err);
						}
						disabled = true;
					}
					else
					{
						level++;

						current = new TagInfo(level > 0, level, i);
						current.content(current.content() + "<");
						currents.put(level, current);
					}
				}
				else if (c == '>')
				{
					if (level < 0)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err(
									"[ERROR] TagParser Logs error : need as many opening and closing tags !", contentIn,
									i, i);

							throw new Exception(err);
						}
						if (TagParser.LOG_WARN)
						{
							final var err = TagParser.err(
									"[WARNING] TagParser Logs error : need as many opening and closing tags !",
									contentIn, i, i);

							System.err.println(err);
						}
					}

					final var tagInfo = currents.get(level);

					if (tagInfo == null)
					{
						if (TagParser.HIGH_SEVERITY)
						{
							final var err = TagParser.err("[ERROR] TagParser Logs error : compilation failed, tag at \""
									+ level + "\" level is not defined !", contentIn, i, i);

							throw new Exception(err);
						}
						final var err = TagParser.err("[WARNING] TagParser Logs error : compilation failed, tag at \""
								+ level + "\" level is not defined !", contentIn, i, i);

						System.err.println(err);
					}
					else
					{
						currents.remove(level);
						tags.add(tagInfo);

						tagInfo.end(i);

						tagInfo.content(tagInfo.content() + ">");
						tagInfo.name(tagInfo.content().replace("<", "").replace(">", ""));
					}
					level--;
				}
				else if (("" + c).matches("[a-zA-Z0-9:\\._$]"))
				{
					current.content(current.content() + c);
				}
			}
			else if (c == '>')
			{
				disabled = false;
			}
		}

		return tags;
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
}