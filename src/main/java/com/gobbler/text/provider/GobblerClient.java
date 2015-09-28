package com.gobbler.text.provider;

import static com.google.common.collect.ImmutableList.of;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Created by vm023561 on 9/28/15.
 */
public class GobblerClient extends Thread
{
    private Socket gobblerClientSocket;


    private static String output = null;

    public GobblerClient (final Socket gobblerClientSocket)
    {
        this.gobblerClientSocket = gobblerClientSocket;
    }

    public void run ()
    {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try
        {
            inp = gobblerClientSocket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(gobblerClientSocket.getOutputStream());
        } catch (IOException e)
        {
            return;
        }

        String line;
        while (true)
        {
            try
            {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT"))
                {
                    gobblerClientSocket.close();
                    return;
                } else
                {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
        }

    }
}
