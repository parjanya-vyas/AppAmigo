package com.libraries.parjanya.recorderviewslib.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.RecorderEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class NetworkUtils {

    private static PrintWriter outputChannel;
    private static BufferedReader inputChannel;

    private static Socket serverSocket;
    private static Socket clientSocket;

    public static void startListening(final Socket serverSideSocket, final Context context) {
        serverSocket = serverSideSocket;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    inputChannel = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                    while (true) {
                        if (inputChannel.ready()) {
                            String msg = inputChannel.readLine();
                            if (msg!=null && !msg.isEmpty()) {
                                Log.d("Server", msg);
                                if (msg.equals(Constants.DISCONNECT_MESSAGE)) {
                                    inputChannel.close();
                                    serverSocket.close();
                                    Log.d("Server", "Closing server!");
                                    return;
                                }
                                final RecorderEvent recorderEvent = Utils.deserializeEvent(msg, null);
                                Handler handler = new Handler(context.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        recorderEvent.playEvent(Utils.getCurrentActivity());
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void initializeOutputChannel(final Socket clientSideSocket) {
        clientSocket = clientSideSocket;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputChannel = new PrintWriter(clientSideSocket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendToPeer(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                outputChannel.println(msg);
            }
        }).start();
    }

    public static void disconnectClient() {
        try {
            sendToPeer(Constants.DISCONNECT_MESSAGE);
            outputChannel.close();
            clientSocket.close();
            Utils.setRecordingState(false);
            Utils.getCurrentActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static Socket startServer() {
        ServerSocket listener;
        try {
            listener = new ServerSocket(Constants.SERVER_PORT);
            Socket socket = listener.accept();
            listener.close();
            return socket;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Socket connectToServer(String serverIP) {
        try {
            return new Socket(serverIP, Constants.SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
