package com.jy.study.nat.executor.result;

import com.jy.study.nat.entity.Message;

public class CommandExecuteResult extends Result{

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
