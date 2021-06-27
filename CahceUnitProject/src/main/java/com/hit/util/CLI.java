package main.java.com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CLI implements Runnable {

    private final PropertyChangeSupport pcls;
    private final BufferedReader reader;
    private final OutputStream writer;

    public CLI(InputStream in, OutputStream out) {
        reader = new BufferedReader(new InputStreamReader(in));
        pcls = new PropertyChangeSupport(this);
        writer = out;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcls.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcls.removePropertyChangeListener(pcl);
    }

    public void write(String string) {
        try {
            writer.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        write("Please enter your command:\n");
        while (true) {
            try {
                String command = reader.readLine();
                switch (command) {
                    case "start":
                        write("Starting server.......\n");
                        pcls.firePropertyChange("command", null, command);
                        break;
                    case "stop":
                        write("Shutdown server\n");
                        pcls.firePropertyChange("command", null, command);
                        break;
                    default:
                        write("Not a valid command\n");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Exception :" + e);
            }
        }
    }
}
