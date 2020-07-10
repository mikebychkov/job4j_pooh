package ru.job4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {

    private ServerSocket ss;
    private ConcurrentHashMap<String, BlockingQueue<String>> queueList = new ConcurrentHashMap<>();
    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Server() {
        try {
            ss = new ServerSocket(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (!ss.isClosed()) {
            try {
                System.out.println(Thread.currentThread().getName() + " : " + "Server waiting for connection...");
                Socket so = ss.accept();
                System.out.println(Thread.currentThread().getName() + " : " + "Connection accepted...");
                pool.submit(new ClientHandler(so, this));
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public void stop() {
        System.out.println(Thread.currentThread().getName() + " : " + "Shutting down thread pool...");
        pool.shutdown();
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!pool.isTerminated()) {
            System.out.println(Thread.currentThread().getName() + " : " + "Waiting while thread pool is terminated...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<String> getQueue(String key, boolean getCopy) {
        BlockingQueue<String> rsl = queueList.get(key);
        if (rsl == null) {
            rsl = new LinkedBlockingQueue<>();
            queueList.put(key, rsl);
        }
        if (getCopy) {
            return new LinkedBlockingQueue<>(rsl);
        }
        return rsl;
    }
}
