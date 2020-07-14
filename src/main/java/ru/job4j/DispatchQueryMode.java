package ru.job4j;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class DispatchQueryMode {

    private final Map<String, Function<DispatchQueryModeParam, Boolean>> dispatch = new HashMap<>();

    public DispatchQueryMode() {
        init();
    }

    public void put(String key, Function<DispatchQueryModeParam, Boolean> f) {
        dispatch.put(key, f);
    }

    public void init() {
        put("POST",
                (param) -> {
                    BufferedReader reader = param.getReader();
                    BlockingQueue<String> queue = param.getQueue();
                    try {
                        System.out.println(Thread.currentThread().getName() + " : " + "Reading request data...");
                        String inData = reader.readLine();
                        System.out.println(Thread.currentThread().getName() + " : " + "Request data: " + inData);
                        queue.add(inData);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
        );
        put("GET",
                (param) -> {
                    PrintWriter writer = param.getWriter();
                    BlockingQueue<String> queue = param.getQueue();
                    try {
                        System.out.println(Thread.currentThread().getName() + " : " + "Polling data from queue...");
                        String outData = queue.poll();
                        System.out.println(Thread.currentThread().getName() + " : " + "Data from queue: " + outData);
                        writer.println(outData);
                        writer.flush();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
        );
    }

    public boolean dispatch(DispatchQueryModeParam param) {
        Function<DispatchQueryModeParam, Boolean> f = dispatch.get(param.getMode());
        if (f != null) {
            return f.apply(param);
        }
        throw new IllegalStateException("Handle not defined for the mode: " + param.getMode());
    }
}
