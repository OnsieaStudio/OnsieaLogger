/**
 *
 */
package fr.onsiea.logger.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Seynax
 *
 */
public class FileUtils
{
	public void write(String filepathIn, String contentIn) throws RuntimeException
	{
		final var file = new File(filepathIn);

		if (file.isDirectory())
		{
			throw new RuntimeException("\"" + filepathIn + "\" is a directory ! Impossible to write logs in it !");
		}

		BufferedWriter bufferedWriter = null;
		try
		{
			if (!file.exists() && !file.createNewFile())
			{
				throw new RuntimeException("\"" + filepathIn + "\" does not exist and could not be created !");
			}

			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bufferedWriter != null)
			{
				try
				{
					bufferedWriter.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}