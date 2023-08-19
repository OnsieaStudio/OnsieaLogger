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
package fr.onsiea.logger;

/**
 * @author Seynax
 */
public class OnsieaLogger
{
	// Version info

	public final static int VERSION_MAJOR = 2;
	public final static int VERSION_MINOR = 1;
	public final static String VERSION = OnsieaLogger.VERSION_MAJOR + "." + OnsieaLogger.VERSION_MINOR;
	public final static String VERSION_DATE = "02/20/2022 18h49";

	private static EnumSeverity defaultSeverity = EnumSeverity.INFORMATION;
	private static EnumSeverity defaultErrorSeverity = EnumSeverity.ERROR;

	public static EnumSeverity defaultSeverity()
	{
		return OnsieaLogger.defaultSeverity;
	}

	public static void defaultSeverity(EnumSeverity defaultSeverityIn)
	{
		OnsieaLogger.defaultSeverity = defaultSeverityIn;
	}

	public static EnumSeverity defaultErrorSeverity()
	{
		return OnsieaLogger.defaultErrorSeverity;
	}

	public static void defaultErrorSeverity(EnumSeverity defaultErrorSeverityIn)
	{
		OnsieaLogger.defaultErrorSeverity = defaultErrorSeverityIn;
	}
}