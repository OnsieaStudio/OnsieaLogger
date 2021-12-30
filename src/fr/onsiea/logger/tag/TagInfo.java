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

import java.util.Objects;

/**
 * @author Seynax
 *
 */
public class TagInfo
{
	private boolean	encapsulated;
	private int		level;
	private String	name;
	private String	content;
	private int		start;
	private int		end;

	public TagInfo(boolean encapsulatedIn, int levelIn, int startIn)
	{
		this.encapsulated(encapsulatedIn);
		this.level(levelIn);
		this.start(startIn);
		this.content("");
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.content, this.encapsulated, this.end, this.level, this.name, this.start);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (TagInfo) obj;
		return Objects.equals(this.content, other.content) && this.encapsulated == other.encapsulated
				&& this.end == other.end && this.level == other.level && Objects.equals(this.name, other.name)
				&& this.start == other.start;
	}

	@Override
	public String toString()
	{
		return "TagInfo [encapsulated=" + this.encapsulated + ", level=" + this.level + ", name=" + this.name
				+ ", content=" + this.content + ", start=" + this.start + ", end=" + this.end + "]";
	}

	public boolean encapsulated()
	{
		return this.encapsulated;
	}

	private void encapsulated(boolean encapsulatedIn)
	{
		this.encapsulated = encapsulatedIn;
	}

	public int level()
	{
		return this.level;
	}

	private void level(int levelIn)
	{
		this.level = levelIn;
	}

	public String name()
	{
		return this.name;
	}

	public void name(String nameIn)
	{
		this.name = nameIn;
	}

	public String content()
	{
		return this.content;
	}

	public void content(String contentIn)
	{
		this.content = contentIn;
	}

	public int start()
	{
		return this.start;
	}

	private void start(int startIn)
	{
		this.start = startIn;
	}

	public int end()
	{
		return this.end;
	}

	public void end(int endIn)
	{
		this.end = endIn;
	}
}
