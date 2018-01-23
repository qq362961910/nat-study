package com.jy.study.nat.handler;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.exception.SocketClosedException;
import com.jy.study.nat.util.MessageUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class LengthMessageReader {

    private final BufferedInputStream in;

    /**
     * 消息长度
     * */
    private int msgLength = -1;
    /**
     * buffer1
     * */
    private byte[] buff1 = new byte[8192];
    /**
     * buff2
     * */
    private byte[] buff2 = new byte[8192];
    /**
     * buff index
     * */
    private int index = 0;
    /**
     * buff position mark
     * */
    private boolean isFirst = true;
    /**
     * tmp
     * */
    private byte[] bytes = new byte[4096];

    public Message readMessage() throws IOException, SocketClosedException {
        //读取消息长度(4个字节)
        if(msgLength == -1) {
            msgLength = in.read();
            if(msgLength == -1) {
                throw new SocketClosedException();
            }
        }
        //读取消息内容
        int len = in.read(bytes);
        if(len == -1) {
            throw new SocketClosedException();
        }
        else {
            //把读取的内容复制到buffer并移动buffer下标
            byte[] currentBuff = getCurrentBuff();
            System.arraycopy(bytes, 0, currentBuff, index, len);
            index+=len;
            //如果消息内容完整
            if(index>=msgLength) {
                //截取使用的字节数组转换成消息
                Message in = MessageUtil.translate(Arrays.copyOfRange(currentBuff, 0, msgLength));
                //将剩余的字节复制到另外一个buffer,并标记下标
                int copyLength = index - msgLength;
                byte[] otherBuff = getOtherBuff();
                System.arraycopy(currentBuff, msgLength, otherBuff, 0, copyLength);
                index = copyLength;
                //复位标志状态
                msgLength = -1;
                changBuff();
                return in;
            } else {
                return null;
            }
        }
    }

    public LengthMessageReader(BufferedInputStream in) {
        this.in = in;
    }
    private byte[] getCurrentBuff() {
        return isFirst ? buff1 : buff2;
    }
    private byte[] getOtherBuff() {
        return isFirst ? buff2 : buff1;
    }
    private void changBuff() {
        isFirst = !isFirst;
    }
}
