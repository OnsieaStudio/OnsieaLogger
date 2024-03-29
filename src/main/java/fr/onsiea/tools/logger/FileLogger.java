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
import lombok.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Seynax
 */

@AllArgsConstructor(staticName = "of")
@ToString
@Getter
@Setter
public class FileLogger extends BaseLogger implements ILogger
{
	private File outFile;
	private File errFile;

	private String outFilepath;
	private String errFilepath;
	private String outContent;
	private String errContent;

	private long saveTime;

	private long last;

	private FileLogger(String outFilepathIn, String errFilepathIn, long saveTimeIn, boolean appendIn, String patternIn)
			throws IOException
	{
		this.outFilepath(outFilepathIn);
		this.errFilepath(errFilepathIn);

		final var outFile = new File(this.outFilepath());
		if (!outFile.getParentFile().exists())
		{
			if (!outFile.getParentFile().mkdirs())
			{
				return;
			}
		}
		if (!outFile.exists())
		{
			if (!outFile.createNewFile())

			{
				return;
			}
		}
		this.outFile(outFile);

		final var errFile = new File(this.errFilepath());
		if (!errFile.getParentFile().exists())
		{
			if (!errFile.getParentFile().mkdirs())
			{
				return;
			}
		}
		if (!errFile.exists())
		{
			if (!errFile.createNewFile())
			{
				return;
			}
		}
		this.errFile(errFile);

		this.saveTime(saveTimeIn);

		this.tagParser(new TagParser(patternIn));

		this.outContent("");
		this.errContent("");
	}

	@Override
	protected ILogger printErr(String contentIn)
	{
		this.errContent += contentIn;
		try
		{
			this.saveRuntime();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return this;
	}

	@Override
	protected ILogger print(String contentIn)
	{
		this.outContent += contentIn;
		try
		{
			this.saveRuntime();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
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
		try (var bufferedWriter = new BufferedWriter(new FileWriter(this.errFile(), true)))
		{
			bufferedWriter.write(this.errContent());
			bufferedWriter.close();
			this.errContent("");
		}
		try (var bufferedWriter = new BufferedWriter(new FileWriter(this.outFile(), true)))
		{
			bufferedWriter.write(this.outContent());
			this.outContent("");
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	@Setter(AccessLevel.PUBLIC)
	@Getter(AccessLevel.PRIVATE)
	public final static class Builder
	{
		private String outFilepath;
		private String errFilepath;
		private long saveTime;
		private String pattern;
		private boolean append;

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