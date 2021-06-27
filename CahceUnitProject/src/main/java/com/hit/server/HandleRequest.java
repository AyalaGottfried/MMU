package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.services.CacheUnitController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest<T> implements Runnable {
    private Socket socket;
    private CacheUnitController<T> controller;
    private DataOutputStream output;
    private InputStream input;

    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        try {
            socket = s;
            this.controller = controller;
            output = new DataOutputStream(s.getOutputStream());
            input = s.getInputStream();
        } catch (IOException e) {
            System.out.println("Exception :" + e);
        }
    }

    @Override
    public void run() {
        Scanner reader = new Scanner(new InputStreamReader(input));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
        String req = reader.nextLine();
        Type requestType = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        Request<DataModel<T>[]> request = new Gson().fromJson(req, requestType);
        String action = request.getHeaders().get("action");
        DataModel<T>[] body = request.getBody();
        switch (action) {
            case "UPDATE":
                writer.println(controller.update(body));
                break;
            case "GET":
                writer.println(new Gson().toJson(controller.get(body)));
                break;
            case "DELETE":
                writer.println(controller.delete(body));
                break;
            case "STATISTICS":
                writer.println(controller.getStatistics());
                break;
            default:
                break;
        }
        writer.flush();
        writer.close();
        reader.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception :" + e);
        }
    }
}
