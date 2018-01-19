package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnResponseCommandExecutor extends CommandExecutor {

    @Override
    public void doExecute(ChannelContext context, Message message) {
        ClientRecord currentClientRecord = context.getClientRecord();
        String[] entry = message.getContent().split(":");
        String targetIp = entry[0];
        int targetPort = Integer.parseInt(entry[1]);
        ClientRecord targetClientRecord = new ClientRecord(targetIp, targetPort);
        Socket socket = context.getServerContext().getClientRecordSocketMap().get(targetClientRecord);
        if(socket == null) {
            System.out.println("目标用户不在线");
        } else {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(socket.getOutputStream());
                Message msg = new Message();
                msg.setCommand(CommandConstants.conn);
                msg.setContent(currentClientRecord.getHost() + ":" + currentClientRecord.getPort());
                writeMessage(out, msg);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(out != null) {
                    try { out.close(); } catch (IOException e) { e.printStackTrace(); }
                }
            }
        }
    }
    public ConnResponseCommandExecutor() {
        super(CommandConstants.conn);
    }
}
