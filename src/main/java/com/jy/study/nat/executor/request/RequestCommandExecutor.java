package com.jy.study.nat.executor.request;

import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;

public interface RequestCommandExecutor {

    void execute(ChannelContext context, String command) throws IOException;

    boolean apply(String command);
}
