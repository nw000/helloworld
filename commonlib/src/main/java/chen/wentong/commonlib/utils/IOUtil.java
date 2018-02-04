package chen.wentong.commonlib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：
 */

public class IOUtil {
    /**
     * 复制对象
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T  copy(T t) throws Exception{
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(t);
        oos.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
    }
}
