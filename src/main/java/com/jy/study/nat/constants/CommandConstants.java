package com.jy.study.nat.constants;

import java.util.ArrayList;
import java.util.List;

public class CommandConstants {

    public static final String list = "list";

    public static final String list_response = "list-response";

    public static final String conn = "conn";

    public static final String conn_response = "conn-response";

    public static final List<String> commandList = new ArrayList<>();

    static {
        commandList.add(list);
        commandList.add(conn);
    }

}
