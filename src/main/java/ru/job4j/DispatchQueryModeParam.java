package ru.job4j;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class DispatchQueryModeParam {

    private String mode;
    private BlockingQueue<String> queue;
    private BufferedReader reader;
    private PrintWriter writer;

    public DispatchQueryModeParam(String mode, BlockingQueue<String> queue, BufferedReader reader, PrintWriter writer) {
        this.mode = mode;
        this.queue = queue;
        this.reader = reader;
        this.writer = writer;
    }

    public String getMode() {
        return mode;
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
