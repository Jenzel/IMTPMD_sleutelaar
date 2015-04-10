package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;

/**
* Created by Jens Brokaar
* s1066589
* IMTPMD
*/

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ServerCommunicator extends AsyncTask<Void, Void, String> {

    private Activity activity;
    private String server_message;
    private String ipadres;
    private int port;
    private String response = null;

    public ServerCommunicator(String ipadres, int port, String server_message ){
        super();

        this.server_message = server_message;
        this.ipadres = ipadres;
        this.port = port;
    }



    @Override
    protected String doInBackground(Void... params) {
        try {
            Socket serverSocket = new Socket();
            serverSocket.connect(new InetSocketAddress(this.ipadres, this.port), 4444);
            this.sendMessage(server_message, serverSocket);
            InputStream input;

            try {
                input = serverSocket.getInputStream();
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(input));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                this.response = stringBuilder.toString();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendMessage(String message, Socket serverSocket) {
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(serverSocket.getOutputStream());
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        if (outputStreamWriter != null) {
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            PrintWriter writer = new PrintWriter(bufferedWriter, true);

            writer.println(message);
        }
    }
}
