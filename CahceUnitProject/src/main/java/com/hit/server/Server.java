package main.java.com.hit.server;

import main.java.com.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements PropertyChangeListener, Runnable {
    private final CacheUnitController<String> controller;
    private boolean isOn;

    public Server() {
        controller = new CacheUnitController<String>();
        isOn = false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String command = (String) evt.getNewValue();
        if (command.equals("start")) {
            if (!isOn) {
                isOn = true;
                new Thread(this).start();
            }
        }
        if (command.equals("stop")) {
            if (isOn) {
                isOn = false;
            }
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (isOn) {
                Socket socket = serverSocket.accept();
                if (!isOn) {
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.println("the server closed the connection");
                    writer.flush();
                    writer.close();
                    break;
                }
                Thread t = new Thread(new HandleRequest<>(socket, controller));
                t.start();
            }
            controller.saveCacheToIDao();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Exception :" + e);
        }
    }
}
