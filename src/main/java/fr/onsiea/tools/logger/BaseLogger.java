/**
 * Copyright 2021 Onsiea All rights reserved.
 * <p>
 * This file is part of OnsieaLogger. (https://github.com/Onsiea/OnsieaLogger)
 * <p>
 * Unless noted in license (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE.md) notice file
 * (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE_NOTICE.md), OnsieaLogger and all parts herein is licensed
 * under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here
 * https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
 * <p>
 * OnsieaLogger is libre software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3.0 of the License, or (at your option)
 * any later version.
 * <p>
 * OnsieaLogger is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with OnsieaLogger.  If not, see
 * <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
 * <p>
 * Neither the name "Onsiea", "OnsieaLogger", or any derivative name or the names of its authors / contributors may be
 * used to endorse or promote products derived from this software and even less to name another project or other work
 * without clear and precise permissions written in advance.
 * <p>
 * (more details on https://github.com/Onsiea/OnsieaLogger/wiki/License)
 *
 * @author Seynax
 */
package fr.onsiea.tools.logger;

import fr.onsiea.tools.logger.tag.TagParser;
import fr.onsiea.tools.logger.utils.LogUtils;
import fr.onsiea.tools.utils.file.FileUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class BaseLogger implements ILogger
{
	private TagParser tagParser;
	private int stackTraceIncrement = 0;

	protected BaseLogger()
	{
		this.tagParser(new TagParser());
	}

	protected BaseLogger(String patternIn)
	{
		this.tagParser(new TagParser(patternIn));
	}

	@Override
	public ILogger increaseStackTraceIncrement()
	{
		this.stackTraceIncrement++;

		return this;
	}

	@Override
	public ILogger resetStackTraceIncrement()
	{
		this.stackTraceIncrement = 0;

		return this;
	}

	@Override
	public ILogger withPattern(String patternIn)
	{
		this.tagParser().withPattern(patternIn);

		return this;
	}

	@Override
	public ILogger log(EnumSeverity severityIn, Object... objectsIn)
	{
		try
		{
			this.print(severityIn, this.tagParser().parseAndReplace(severityIn, LogUtils.toString(objectsIn),
					Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder().toString());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ILogger logLn(EnumSeverity severityIn, Object... objectsIn)
	{
		try
		{
			this.printLn(severityIn, this.tagParser().parseAndReplace(severityIn, LogUtils.toString(objectsIn),
					Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder().toString()
					+ System.lineSeparator());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ILogger log(Object... objectsIn)
	{
		try
		{
			this.print(this.tagParser().parseAndReplace(OnsieaLogger.defaultSeverity(), LogUtils.toString(objectsIn),
					Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder().toString());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ILogger logLn(Object... objectsIn)
	{
		try
		{
			this.print(this.tagParser().parseAndReplace(OnsieaLogger.defaultSeverity(), LogUtils.toString(objectsIn),
					Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder().toString()
					+ System.lineSeparator());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ILogger logErr(Object... objectsIn)
	{
		try
		{
			this.printErr(this.tagParser()
					.parseAndReplace(OnsieaLogger.defaultErrorSeverity(), LogUtils.toString(objectsIn),
							Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder()
					.toString());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ILogger logErrLn(Object... objectsIn)
	{
		try
		{
			this.printErr(this.tagParser()
					.parseAndReplace(OnsieaLogger.defaultErrorSeverity(), LogUtils.toString(objectsIn),
							Thread.currentThread().getStackTrace()[2 + this.stackTraceIncrement()]).stringBuilder()
					.toString() + System.lineSeparator());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return this;
	}

	public void print(EnumSeverity severityIn, String contentIn)
	{
		if (severityIn.errStream())
		{
			this.printErr(contentIn);
		}
		else
		{
			this.print(contentIn);
		}
	}

	public void printLn(EnumSeverity severityIn, String contentIn)
	{
		if (severityIn.errStream())
		{
			this.printErr(contentIn + System.lineSeparator());
		}
		else
		{
			this.print(contentIn + System.lineSeparator());
		}
	}

	protected abstract ILogger printErr(String contentIn);

	protected abstract ILogger print(String contentIn);
}