package com.jy.study.nat.executor.request;

import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;

public abstract class AbstractRequestCommandExecutor implements RequestCommandExecutor{

    protected final String command;

    @Override
    public void execute(ChannelContext context, String command) throws IOException {
        if(apply(command)) {
            doExecute(context);
        }
    }

    @Override
    public boolean apply(String command) {
        return this.command.equals(command);
    }

    public abstract void doExecute(ChannelContext channelContext) throws IOException;

    public AbstractRequestCommandExecutor(String command) {
        this.command = command;
    }
}
