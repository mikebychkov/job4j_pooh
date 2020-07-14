package ru.job4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements Runnable {

    private Server srv;
    private Socket so;

    public ClientHandler(Socket so, Server srv) {
        this.so = so;
        this.srv = srv;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(so.getInputStream()));
             PrintWriter writer = new PrintWriter(so.getOutputStream())) {

            System.out.println(Thread.currentThread().getName() + " : " + "Client handler started...");

            String[] request = reader.readLine().split("/");

            System.out.println(Thread.currentThread().getName() + " : " + "Request: " + Arrays.toString(request));

            String mode = request[0];
            String type = request[1];
            String queueName = request[2];

            BlockingQueue<String> queue = new LinkedBlockingQueue<>();

            queue = srv.getQueue(queueName, new DispatchQueryType().dispatch(type));

            DispatchQueryModeParam param = new DispatchQueryModeParam(mode, queue, reader, writer);
            new DispatchQueryMode().dispatch(param);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
