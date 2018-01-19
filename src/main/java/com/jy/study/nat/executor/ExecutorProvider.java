package com.jy.study.nat.executor;

import java.util.ArrayList;
import java.util.List;

public class ExecutorProvider {

    public static final List<Executor> SERVER_EXECUTOR_LIST = new ArrayList<>();

    static  {
        SERVER_EXECUTOR_LIST.add(new ListClientsResponseCommandExecutor());
        SERVER_EXECUTOR_LIST.add(new ConnResponseCommandExecutor());
    }

}
