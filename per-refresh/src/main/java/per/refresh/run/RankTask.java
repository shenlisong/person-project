package per.refresh.run;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import per.refresh.util.ClientProperties;
import per.refresh.util.ReptileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/18.
 */
public class RankTask implements Runnable {

    private final static String WORKIDS = "workIds";

    private static List<String> workIds = null;


    /**
     * 执行扫描和刷新数据到数据库的线程池
     */
    private static ScheduledExecutorService executor;

    /**
     * 获取职位ids
     */
    public static void getWorkIds(){
        workIds = new ArrayList<String>();
        String workIdStr = ClientProperties.getProperty(WORKIDS);
        String[] tmp = workIdStr.split(",");
        for(String str : tmp ){
            str = str.trim();
            if(StringUtils.isNotBlank(str) && !workIds.contains(str)){
                workIds.add(str);
            }
        }
    }


    /**
     * 需要执行的任务
     */
    @Override
    public void run() {

        if(RankTask.workIds == null){
            RankTask.getWorkIds();
        }

        System.out.println(JSON.toJSONString(ReptileUtil.getRank(RankTask.workIds)));
    }


    public static void main(String[] args){

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new RankTask(), 0, 20, TimeUnit.SECONDS);
    }
}
