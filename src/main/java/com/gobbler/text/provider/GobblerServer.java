package com.gobbler.text.provider;

import static com.gobbler.text.provider.Constants.CACHE_SIZE;
import static com.gobbler.text.provider.Constants.PORT_NUMBER;
import static com.gobbler.text.provider.Constants.supportedCommands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * The Server implementation of the Gobbler project.
 * @author Venkatesh Muthusamy
 */
public class GobblerServer
{

    private static final LRUCache<Long, String> cache = new LRUCache<Long, String>(CACHE_SIZE);

    private static Socket clientSocket = null;
    private static LineIterator lineIterator = null;
    private static File fileFromPath = null;

    private static ServerSocket serverSocket = null;

    public static void main (String args[]) throws IOException
    {
        String path = null;
        if (args.length == 0)
        {

            throw new IllegalArgumentException(
                    "Please pass the path to the file associated with the server");
        }
        if (args.length > 1)
        {
            throw new IllegalArgumentException("Need only one input parameter for this Server");
        }

        if (args.length == 1)
        {
            path = args[0];
        }

        initialize(path);
        try
        {
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e)
        {
            System.err.println(String.format("Could not listen on port: %d", PORT_NUMBER));
            System.exit(1);

        }
        System.out.println(String.format("Gobbler Server Started at %d........", PORT_NUMBER));
        try
        {
            clientSocket = serverSocket.accept();
        } catch (IOException e)
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println("Connection with a Client:");
        System.out.println("Waiting for input.....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        {
            System.out.println("Server: " + inputLine);
            final String buffer = validateCommands(inputLine);
            if (isNumeric(buffer))
            {
                final Long key = Long.parseLong(buffer);
                if (cache.get(key) != null)
                {
                    out.println(cache.get(key));
                } else
                {
                    out.println(getLineFromFile(lineIterator, Long.parseLong(buffer)));
                }
            }else
            {
                out.println(buffer);
            }

        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();

    }

    private static boolean isNumeric (String buffer)
    {
        try
        {
            long l = Long.parseLong(buffer);
        } catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private static void initialize (final String path) throws IOException
    {
        fileFromPath = new File(path);
        verifyFileExists(fileFromPath);

    }

    public static void verifyFileExists (final File file)
    {

        if (!file.exists())
        {
            throw new IllegalArgumentException(("Entered path is not a valid file"));
        }
        if (file.isDirectory())
        {
            throw new IllegalArgumentException("Entered path should be a file not a directory");
        }

    }

    private static String validateCommands (final String input)
    {
        final String[] parts = input.split(" ");

        if (parts.length > 2)
        {
            return "ERR";
        }

        if (parts[0] != null && !supportedCommands.contains(parts[0]))
        {
            return "ERR";
        }

        if (parts[1] != null)
        {
            for (final Character ch : parts[1].toCharArray())
            {
                if (!Character.isDigit(ch))
                {
                    return "ERR";
                }
            }
            return parts[1];
        }

        return "ERR";
    }

    private static String getLineFromFile (LineIterator lit, final long lineNumber)
            throws IOException
    {
        try
        {
            lit = FileUtils.lineIterator(fileFromPath, "UTF-8");
            long count = 0;
            while (lit.hasNext())
            {
                String line = lit.nextLine();
                count++;
                if (lineNumber - 1 == count)
                {
                    cache.put(lineNumber, line);
                    return line;
                }
                // do something with line
            }
        } finally
        {
            LineIterator.closeQuietly(lit);
        }

        return "ERR";
    }

}
