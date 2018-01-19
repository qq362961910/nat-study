package com.jy.study.nat.executor;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.util.MessageUtil;

import java.io.IOException;
import java.io.OutputStream;

public abstract class CommandExecutor implements Executor {

    protected String command;

    public void execute(ChannelContext context, Message message) throws IOException {
        if(command.equals(message.getCommand())) {
            doExecute(context, message);
        }
    }

    public void writeMessage(OutputStream out, Message message) throws IOException {
        byte[] bytes = MessageUtil.translate(message);
        out.write(bytes.length);
        out.write(bytes);
        out.flush();
    }

    public abstract void doExecute(ChannelContext context, Message message) throws IOException;

    public CommandExecutor(String command) {
        this.command = command;
    }

}
