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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import fr.onsiea.logger.utils.LogUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */

@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class FileLogger implements ILogger
{
	private BufferedWriter	out;
	private BufferedWriter	err;
	private String			outFilepath;
	private String			errFilepath;
	private long			saveTime;

	private long			last;

	public FileLogger(String outFilepathIn, String errFilepathIn) throws IOException
	{
		this.outFilepath(outFilepathIn);
		this.errFilepath(errFilepathIn);
		this.out(new BufferedWriter(new FileWriter(new File(this.outFilepath()))));
		this.err(new BufferedWriter(new FileWriter(new File(this.errFilepath()))));
		this.saveTime(-1);
	}

	public FileLogger(String outFilepathIn, String errFilepathIn, long saveTimeIn) throws IOException
	{
		this.outFilepath(outFilepathIn);
		this.errFilepath(errFilepathIn);
		this.out(new BufferedWriter(new FileWriter(new File(this.outFilepath()))));
		this.err(new BufferedWriter(new FileWriter(new File(this.errFilepath()))));
		this.saveTime(saveTimeIn);
	}

	@Override
	public ILogger log(EnumSeverity severityIn, Object... objectsIn)
	{
		this.write(severityIn, "[" + severityIn.alias() + "]" + LogUtils.toString(objectsIn));

		return this;
	}

	@Override
	public ILogger logLn(EnumSeverity severityIn, Object... objectsIn)
	{
		this.write(severityIn, "[" + severityIn.alias() + "]" + LogUtils.toString(objectsIn) + "\n");

		return this;
	}

	@Override
	public ILogger log(Object... objectsIn)
	{
		this.writeOut(LogUtils.toString(objectsIn));

		return this;
	}

	@Override
	public ILogger logLn(Object... objectsIn)
	{
		this.writeOut(LogUtils.toString(objectsIn) + "\n");

		return this;
	}

	@Override
	public ILogger logErr(Object... objectsIn)
	{
		this.writeErr(LogUtils.toString(objectsIn));

		return this;
	}

	@Override
	public ILogger logErrLn(Object... objectsIn)
	{
		this.writeErr(LogUtils.toString(objectsIn) + "\n");

		return this;
	}

	public ILogger write(EnumSeverity severityIn, String contentIn)
	{
		if (severityIn.errStream())
		{
			this.writeErr(contentIn);
		}
		else
		{
			this.writeOut(contentIn);
		}

		return this;
	}

	public ILogger writeOut(String contentIn)
	{
		try
		{
			this.out().write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				this.saveRuntime();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return this;
	}

	public ILogger writeErr(String contentIn)
	{
		try
		{
			this.err().write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				this.saveRuntime();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return this;
	}

	private void saveRuntime() throws IOException
	{
		final var actual = System.nanoTime();
		if (this.saveTime() < 0 || actual - this.last() > this.saveTime())
		{
			this.save();

			this.last(actual);
		}
	}

	public void save() throws IOException
	{
		this.err().close();
		this.out().close();
		this.out(new BufferedWriter(new FileWriter(new File(this.outFilepath()))));
		this.err(new BufferedWriter(new FileWriter(new File(this.errFilepath()))));
	}

	public void close() throws IOException
	{
		this.err().close();
		this.out().close();
	}
}