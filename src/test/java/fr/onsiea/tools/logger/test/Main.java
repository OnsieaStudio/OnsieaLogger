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
package fr.onsiea.tools.logger.test;

import fr.onsiea.tools.logger.ConsoleLogger;
import fr.onsiea.tools.logger.FileLogger;
import fr.onsiea.tools.logger.Loggers;
import fr.onsiea.tools.logger.StreamLogger;

import java.util.Objects;

/**
 * @author Seynax
 */
public class Main
{
	public final static String PATTERN = "<classFullName>/<methodName> [<lineNumber>] [<thread>-<severity>-<severity_alias>]-[<time:HH'h'mm ss's'S'ms'>]-[<date>] <content>";

	public static void main(String[] argsIn)
	{
		System.out.println("OnsieaLogger test start !");
		Main.fileLogger();
		Main.consoleLogger();
		Main.streamLogger();
		Main.consoleAndFileLogger();
		System.out.println("OnsieaLogger test end !");
	}

	public static void fileLogger()
	{
		FileLogger logger = null;

		try
		{
			logger = new FileLogger.Builder("E:\\Java\\projets\\OnsieaLogger\\output\\out.log",
					"E:\\Java\\projets\\OnsieaLogger\\output\\err.log").append(false).pattern(Main.PATTERN).build();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Objects.requireNonNull(logger).logErrLn("A");
			logger.logLn("B");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void consoleLogger()
	{
		ConsoleLogger logger = null;

		try
		{
			logger = new ConsoleLogger(Main.PATTERN);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Objects.requireNonNull(logger).logErrLn("console  - A");
			logger.logLn("console  - B");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void streamLogger()
	{
		StreamLogger logger = null;

		try
		{
			logger = new StreamLogger(System.out, System.err, Main.PATTERN);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Objects.requireNonNull(logger).logErrLn("stream - A");
			logger.logLn("stream - B");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void consoleAndFileLogger()
	{
		Loggers loggers = null;

		try
		{
			loggers = Loggers.consoleAndFile(Main.PATTERN, "E:\\Java\\projets\\OnsieaLogger\\output\\out.log",
					"E:\\Java\\projets\\OnsieaLogger\\output\\err.log");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Objects.requireNonNull(loggers).logErrLn("consoleAndFile - A");
			loggers.logLn("consoleAndFile - B");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}