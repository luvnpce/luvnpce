package rpcdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeUtil {

    static ByteArrayOutputStream bout = new ByteArrayOutputStream();

    public static synchronized byte[] doSerialize(Object obj)  {

        try {
            bout.reset();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(obj);
            return bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
