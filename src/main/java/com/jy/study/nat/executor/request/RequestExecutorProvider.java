package com.jy.study.nat.executor.request;

import java.util.ArrayList;
import java.util.List;

public class RequestExecutorProvider {

    public static final List<RequestCommandExecutor> CLIENT_EXECUTOR_LIST = new ArrayList<>();

    static  {
        CLIENT_EXECUTOR_LIST.add(new ListClientsRequestCommandExecutor());
        CLIENT_EXECUTOR_LIST.add(new ConnRequestCommandExecutor());
    }
}
