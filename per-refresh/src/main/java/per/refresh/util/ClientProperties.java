package per.refresh.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ClientProperties {

    private static Properties p;

    static
    {
        init();
    }

    public static void init()
    {
        p = new Properties();
        try
        {
            String path = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("")
                    .toURI()
                    .getPath();

            FileInputStream inputStream = new FileInputStream(path +
                    "Client.properties");

        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Client.properties文件不存在");
            return;
        }
        catch (IOException e)
        {
            System.out.println("Client.properties文件解析失败");
            return;
        }
    }

    public static String getProperty(String key)
    {
        return p.getProperty(key);
    }

    public static void main(String[] args)
    {
        System.out.println(getProperty("id"));
    }
}
