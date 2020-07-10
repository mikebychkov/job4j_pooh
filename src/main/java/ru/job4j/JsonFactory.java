package ru.job4j;

import java.util.StringJoiner;

public class JsonFactory implements FormatFactory {

    private String type;

    public JsonFactory(String type) {
        this.type = type;
    }

    public String getData(String name, String text) {
        StringJoiner sj = new StringJoiner("");

        sj.add("{");
        sj.add(String.format("\"%s\":\"%s\"", type, name));
        sj.add(",");
        sj.add(String.format("\"text\":\"%s\"", text));
        sj.add("}");

        return sj.toString();
    }
}
