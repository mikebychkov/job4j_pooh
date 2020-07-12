package ru.job4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public Client() {
    }

    public void post(String type, String name, String text) {
        FormatFactory ff = new JsonFactory(type);
        try {
            System.out.println(Thread.currentThread().getName() + " : " + "Establishing connection to the server...");
            Socket so = new Socket("127.0.0.1", 8080);
            try (PrintWriter writer = new PrintWriter(so.getOutputStream())) {

                System.out.println(Thread.currentThread().getName() + " : " + "Writing query to the server...");

                writer.printf("POST/%s/%s%n", type, name);
                writer.flush();
                writer.println(ff.getData(name, text));
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String type, String name) {
        String rsl = null;
        try {
            System.out.println(Thread.currentThread().getName() + " : " + "Establishing connection to the server...");
            Socket so = new Socket("127.0.0.1", 8080);
            try (PrintWriter writer = new PrintWriter(so.getOutputStream());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(so.getInputStream()))) {

                System.out.println(Thread.currentThread().getName() + " : " + "Writing query to the server...");

                writer.printf("GET/%s/%s%n", type, name);
                writer.flush();
                rsl = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
