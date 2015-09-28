package com.gobbler.text.provider;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * Created by vm023561 on 9/26/15.
 */
public class Utility
{

    public static String getLineFromFile (final File path, final int lineNumber)
            throws IOException
    {

        LineIterator lit = FileUtils.lineIterator(path, "UTF-8");
        try
        {
            int count = 0;
            while (lit.hasNext())
            {
                String line = lit.nextLine();
                count++;
                if (lineNumber - 1 == count)
                {
                    return line;
                }
            }
        } finally
        {
            LineIterator.closeQuietly(lit);
        }

        return "ERR";
    }
}
