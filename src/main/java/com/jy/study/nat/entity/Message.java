package com.jy.study.nat.entity;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private String command;

    private Map<String, Object> options = new HashMap<>();

    private String content;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "command='" + command + '\'' +
                ", options=" + options +
                ", content='" + content + '\'' +
                '}';
    }
}
