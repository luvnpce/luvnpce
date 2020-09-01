package rpcdemo.rpc.protocol;

import java.io.Serializable;
import java.util.UUID;

/**
 * 通信的头部协议
 * 1. 标志位
 * 2. request id
 * 3. data length
 */
public class MyHeader implements Serializable {

    int flag;
    long requestId;
    long dataLength;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }

    public static MyHeader assembleHeader(byte[] msgBody) {
        MyHeader header = new MyHeader();
        header.setFlag(0x14141414);
        header.setRequestId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        header.setDataLength(msgBody.length);
        return header;
    }
}
