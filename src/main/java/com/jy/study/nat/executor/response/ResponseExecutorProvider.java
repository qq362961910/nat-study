package com.jy.study.nat.executor.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseExecutorProvider {

    public static final List<ResponseCommandExecutor> SERVER_EXECUTOR_LIST = new ArrayList<>();

    static  {
        SERVER_EXECUTOR_LIST.add(new ListClientsResponseCommandExecutor());
        SERVER_EXECUTOR_LIST.add(new ConnResponseCommandExecutor());
    }

}
