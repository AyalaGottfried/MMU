package main.java.com.hit.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CacheUnitClient {

    public CacheUnitClient() {
    }

    public String send(String request) {
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer.println(request);
            writer.flush();
            String res = reader.nextLine();
            reader.close();
            writer.close();
            socket.close();
            if (res.equals("true")) res = "Succeeded";
            else if (res.equals("false")) res = "Failed";
            else res += "\n\nSucceeded";
            res = res.replace(";", "\n");
            res = res.replace("},", "},\n");
            return res;
        } catch (IOException e) {
            return "error: " + e;
        }
    }
}
