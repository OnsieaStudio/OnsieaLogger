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