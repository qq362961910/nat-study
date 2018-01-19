package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;
import java.util.List;

public class ListClientsResponseCommandExecutor extends CommandExecutor{

    @Override
    public void doExecute(ChannelContext context, Message in) throws IOException {
        Message msg = new Message();
        msg.setCommand(CommandConstants.list_response);
        List<ClientRecord> clientList = context.getServerContext().getClientList();
        StringBuilder sb = new StringBuilder("clients:\r\n");
        int index = 1;
        for (ClientRecord record: clientList) {
            sb.append("client.").append(index++).append("  ").append(record.getHost()).append(":").append(record.getPort()).append("\r\n");
        }
        msg.setContent(sb.toString());
        writeMessage(context.getOut(), msg);
    }

    public ListClientsResponseCommandExecutor() {
        super(CommandConstants.list);
    }
}
