package per.refresh.util;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/10/11.
 */
public class HttpClientUtil {

    private final static String FILENAME = "tmp.html";

    public static void get(String url){

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try{
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                InputStream inputStream = httpResponse.getEntity().getContent();
                FileUtil.downloadFile(inputStream);
            }else{
                System.out.println("访问地址：" + url + "失败");
                httpClient.close();
            }
        } catch (ClientProtocolException e) {
            System.out.println("httpClient异常" + e.getStackTrace());
        } catch (IOException e) {
            System.out.println("httpClient异常" + e.getStackTrace());
        } finally {
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        get("http://www.doumi.com/hz/");
    }


}
