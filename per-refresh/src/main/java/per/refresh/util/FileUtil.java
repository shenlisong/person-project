package per.refresh.util;

import java.io.*;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FileUtil {

    public final static String FILENAME = "tmp.html";

    public static void downloadFile(InputStream inputStream) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME)));
            String strContext = "";
            while ((strContext = bufferedReader.readLine()) != null) {
                bufferedWriter.write(strContext);
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            System.out.println("io操作失败");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("关闭流失败");
            }
        }

    }

    public static void printFileString(final String fileName, final String line) {

        BufferedWriter bufferedWriter = null;
        try {
             bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName))));

            bufferedWriter.write(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void deleteFile() {

        if (fileIsExists()) {
            File file = new File(FILENAME);
            if (file != null) {
                file.delete();
            }
        }

    }

    public static boolean fileIsExists() {
        File file = new File(FILENAME);
        return file != null && file.exists();
    }
}
