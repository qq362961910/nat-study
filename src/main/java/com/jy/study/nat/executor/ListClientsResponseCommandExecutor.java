package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.result.CommandExecuteResult;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.server.Server;

import java.util.List;

public class ListClientsResponseCommandExecutor extends CommandExecutor{

    @Override
    public CommandExecuteResult doExecute(ChannelContext context, Message in) {
        Message msg = new Message();
        msg.setCommand(CommandConstants.list_response);
        List<ClientRecord> clientList = Server.clientList;
        StringBuilder sb = new StringBuilder("clients:\r\n");
        int index = 1;
        for (ClientRecord record: clientList) {
            sb.append("client.").append(index++).append("  ").append(record.getHost()).append(":").append(record.getPort()).append("\r\n");
        }
        msg.setContent(sb.toString());
        CommandExecuteResult result = new CommandExecuteResult();
        result.setSuccess(true);
        result.setMessage(msg);
        return result;
    }

    public ListClientsResponseCommandExecutor() {
        super(CommandConstants.list);
    }
}
