package rpcdemo.rpc.protocol;

/**
 * 通信的数据包
 * - 包含了MyHeader和MyContent
 */
public class Package {

    private MyHeader header;

    private MyContent content;

    public Package(MyHeader header, MyContent content) {
        this.header = header;
        this.content = content;
    }

    public MyHeader getHeader() {
        return header;
    }

    public void setHeader(MyHeader header) {
        this.header = header;
    }

    public MyContent getContent() {
        return content;
    }

    public void setContent(MyContent content) {
        this.content = content;
    }
}
