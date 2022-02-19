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

import fr.onsiea.logger.tag.TagParser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */

@AllArgsConstructor(staticName = "of")
@ToString
@Getter
@Setter
public class FileLogger extends BaseLogger implements ILogger
{
	private BufferedWriter	out;
	private BufferedWriter	err;
	private String			outFilepath;
	private String			errFilepath;
	private long			saveTime;

	private long			last;

	private FileLogger(String outFilepathIn, String errFilepathIn, long saveTimeIn, boolean appendIn, String patternIn)
			throws IOException
	{
		this.outFilepath(outFilepathIn);
		this.errFilepath(errFilepathIn);

		final var outFile = new File(this.outFilepath());
		if (!outFile.getParentFile().exists())
		{
			outFile.getParentFile().mkdirs();
		}
		if (!outFile.exists())
		{
			outFile.createNewFile();
		}

		this.out(new BufferedWriter(new FileWriter(outFile, appendIn)));

		final var errFile = new File(this.errFilepath());
		if (!errFile.getParentFile().exists())
		{
			errFile.getParentFile().mkdirs();
		}
		if (!errFile.exists())
		{
			errFile.createNewFile();
		}

		this.err(new BufferedWriter(new FileWriter(errFile, appendIn)));
		this.saveTime(saveTimeIn);

		this.tagParser(new TagParser(patternIn));
	}

	@Override
	public ILogger withPattern(String patterIn)
	{
		this.tagParser().withPattern(patterIn);

		return this;
	}

	@Override
	protected ILogger printErr(String contentIn)
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

	@Override
	protected ILogger print(String contentIn)
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
		this.out(new BufferedWriter(new FileWriter(new File(this.outFilepath()), true)));
		this.err(new BufferedWriter(new FileWriter(new File(this.errFilepath()), true)));
	}

	public void close() throws IOException
	{
		this.err().close();
		this.out().close();
	}

	@Setter(AccessLevel.PUBLIC)
	@Getter(AccessLevel.PRIVATE)
	public final static class Builder
	{
		private String	outFilepath;
		private String	errFilepath;
		private long	saveTime;
		private String	pattern;
		private boolean	append;

		public Builder(String filepathIn)
		{
			this.outFilepath(filepathIn);
			this.errFilepath(filepathIn);
			this.pattern("<content>");
			this.saveTime(-1);
		}

		public Builder(String outFilepathIn, String errFilepathIn)
		{
			this.outFilepath(outFilepathIn);
			this.errFilepath(errFilepathIn);
			this.pattern("<content>");
			this.saveTime(-1);
		}

		public FileLogger build() throws Exception
		{
			if (this.outFilepath() == null)
			{
				throw new Exception("[FileLogger] Out filepath is null !");
			}
			if (this.errFilepath() == null)
			{
				throw new Exception("[FileLogger] Err filepath is null !");
			}
			if (this.pattern() == null)
			{
				throw new Exception("[FileLogger] Log pattern is null !");
			}

			return new FileLogger(this.outFilepath(), this.errFilepath(), this.saveTime(), this.append(),
					this.pattern());
		}
	}
}