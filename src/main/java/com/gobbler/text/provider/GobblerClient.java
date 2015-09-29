package com.gobbler.text.provider;

import static com.gobbler.text.provider.Constants.PORT_NUMBER;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by vm023561 on 9/28/15.
 */
public class GobblerClient
{
    public static void main (String[] args) throws IOException
    {

        String serverHostname = new String("localhost");

        if (args.length > 0)
        {
            serverHostname = args[0];
        }
        System.out.println(
                String.format("Attempting to connect to Gobbler Server running at %s on port %d",
                        serverHostname, PORT_NUMBER));

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try
        {
            echoSocket = new Socket(serverHostname, PORT_NUMBER);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e)
        {
            final String message = String.format("Cannot resolve host %s", serverHostname);
            System.err.println(message);
            System.exit(1);
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        System.out.print("input: ");
        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);
            System.out.println("CLIENT: " + in.readLine());
            System.out.print("input: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
