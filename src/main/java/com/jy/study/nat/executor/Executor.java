package com.jy.study.nat.executor;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.result.Result;
import com.jy.study.nat.server.ChannelContext;

public interface Executor {
    Result execute(ChannelContext context, Message message);
}
