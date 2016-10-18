package per.refresh.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class HTHelper {

    private static final String loginUrl = "http://vip.doumi.com/employer/user/ajaxlogin";
    private static final String dataUrl = "http://vip.doumi.com/employer/post/ajaxrefreshpost?id=";
    private static HttpClient httpclient = new DefaultHttpClient();

    private static boolean isLogin = false;

    public static void sendHttp(String username, String password, List<String> workIdList) {
        long begin = System.currentTimeMillis();
        if (!isLogin) {
            login(username, password);
        }

        for (String id : workIdList) {
            begin = System.currentTimeMillis();
            get("http://vip.doumi.com/employer/post/ajaxrefreshpost?id=" + id);
            System.out.println(" [Get:" + id + "] " + (System.currentTimeMillis() - begin));
        }
    }

    public static void login(String username, String password) {
        long begin = System.currentTimeMillis();
        List list = new ArrayList();
        list.add(new BasicNameValuePair("phone_login", username));
        list.add(new BasicNameValuePair("passwd_login", password));

        String resultMsg = post("http://vip.doumi.com/employer/user/ajaxlogin", "UTF-8", "UTF-8", list);
        System.out.println(" [Post] " + (System.currentTimeMillis() - begin));
        try {
            String resultCode = getJsonInfoByKey(resultMsg, "errno", false);
            if ((resultCode != null) && ("0".equals(resultCode.trim()))) {
                System.out.println("Login success.");
            } else {
                loggerErrorInfo();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage().toString());
            loggerErrorInfo();
        }

        isLogin = true;
    }

    private static void loggerErrorInfo() {
        System.out.println("Login failed, please check the username or password and restart.");
        System.exit(0);
    }

    public static void testPost() {
    }

    private static String post(String url, String reqEncoding, String respEncoding, List<NameValuePair> param) {
        String resStr = "";

        HttpPost httppost = new HttpPost(url);

        List formparams = param;
        try {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, reqEncoding);
            httppost.setEntity(uefEntity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity, respEncoding);
            }

            System.out.print(response.getStatusLine() + " /" + getJsonInfoByKey(resStr, "errno", false));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resStr;
    }

    private static String get(String url) {
        String resStr = "";
        try {
            HttpGet httpget = new HttpGet(url);

            HttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity);
            }

            System.out.print(response.getStatusLine() + " /" + getJsonInfoByKey(resStr, "code", false));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resStr;
    }

    private static String getJsonInfoByKey(String jsonStr, String key, boolean isUnicode) {
        JSONObject obj = JSONObject.parseObject(jsonStr);

        String value = obj.getString(key);

        return isUnicode ? unicode2String(value) : value;
    }

    private static String unicode2String(String unicode) {
        try {
            return new String(unicode.getBytes("Unicode"), "UTF-16");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
    }
}
