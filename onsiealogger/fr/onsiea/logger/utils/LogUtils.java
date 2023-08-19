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
package fr.onsiea.logger.utils;

import java.io.PrintStream;

/**
 * @author Seynax
 */
public class LogUtils
{
	public static String toString(Object... objectsIn)
	{
		final var content = new StringBuilder();

		for (final Object object : objectsIn)
		{
			content.append(object.toString());
		}

		return content.toString();
	}

	public static void log(Object... objectsIn)
	{
		System.out.print(LogUtils.toString(objectsIn));
	}

	public static void logLn(Object... objectsIn)
	{
		System.out.println(LogUtils.toString(objectsIn));
	}

	public static void logErr(Object... objectsIn)
	{
		System.err.print(LogUtils.toString(objectsIn));
	}

	public static void logErrLn(Object... objectsIn)
	{
		System.err.println(LogUtils.toString(objectsIn));
	}

	public static void logIn(PrintStream printStreamIn, Object... objectsIn)
	{
		printStreamIn.print(LogUtils.toString(objectsIn));
	}

	public static void logInLn(PrintStream printStreamIn, Object... objectsIn)
	{
		printStreamIn.println(LogUtils.toString(objectsIn));
	}
}