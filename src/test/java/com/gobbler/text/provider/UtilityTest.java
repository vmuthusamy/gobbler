package com.gobbler.text.provider;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests around {@link Utility}
 */
public class UtilityTest
{

    private static final String path = "/Users/vm023561/Downloads/Wiki/article.xml";

    @Test
    public void testLoad () throws IOException
    {
        long startTime = System.currentTimeMillis();
        System.out.println(Utility.getLineFromFile(new File(path), 5003));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}
