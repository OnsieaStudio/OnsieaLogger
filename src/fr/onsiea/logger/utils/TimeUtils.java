/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of SourceLinker project. (https://github.com/Onsiea/SourceLinker)<br><br>
*
* SourceLinker is [licensed] (https://github.com/Onsiea/SourceLinker/blob/main/LICENSE) under the terms of the "GNU General Public License v3.0" (GPL-3.0).
* https://github.com/Onsiea/SourceLinker/wiki/License#license-and-copyright<br><br>
*
* SourceLinker is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* SourceLinker is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.<br><br>
*
* You should have received a copy of the GNU General Public License
* along with SourceLinker.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "SourceLinker", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.sourcelinker.utils.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Seynax
 *
 */
public class TimeUtils
{
	private final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("HH'h'mm ss's'S");

	public final static String str()
	{
		return TimeUtils.DEFAULT_FORMAT.format(new Date());
	}

	public final static String str(String formatIn)
	{
		return new SimpleDateFormat(formatIn).format(new Date());
	}

	public final static String str(DateFormat dateFormatIn)
	{
		return dateFormatIn.format(new Date());
	}
}