package com.jy.study.nat.executor.response;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;

public interface ResponseCommandExecutor {
    void execute(ChannelContext context, Message message) throws IOException;
}
