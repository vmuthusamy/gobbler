package com.gobbler.text.provider;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * This is an internal class used by this project. Its implementation can change at any time.
 * @author Venkatesh Muthusamy
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
