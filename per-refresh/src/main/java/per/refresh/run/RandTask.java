package per.refresh.run;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import per.refresh.bean.ClientBean;
import per.refresh.util.ClientProperties;
import per.refresh.util.FileUtil;
import per.refresh.util.ReptileUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class RandTask implements Runnable {

    private static List<String> workIdList = null;

    private static ScheduledExecutorService scheduledExecutorService;

    private static List<Integer> preRank = new ArrayList<Integer>();

    private final static String RANKFILE = "workRank.txt";

    public static void getWorkIds(){

        workIdList = new ArrayList<String>();
        String workIdStr = ClientProperties.getProperty(ClientBean.WORKIDS);
        String[] workIds = workIdStr.split(",");
        for(String tmp : workIds){
            tmp = tmp.trim();
            if(StringUtils.isNotBlank(tmp) && !workIdList.contains(tmp)){
                workIdList.add(tmp);
            }
        }

    }

    public static boolean isSameAndPreRank(List<Integer> currRank){
        if(CollectionUtils.isEmpty(currRank) || currRank.size() != workIdList.size()){
            return true;
        }
        if(CollectionUtils.isEmpty(preRank) || preRank.size() != workIdList.size()){
            preRank = currRank;
            return false;
        }

        for(int i = 0; i < preRank.size(); i++){
            if(currRank.get(i) != preRank.get(i)){
                preRank = currRank;
                return false;
            }
        }
        return true;
    }
    @Override
    public void run() {

        System.out.println("时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString() + "  开始：" + JSON.toJSONString(preRank));
        if(workIdList == null){
            getWorkIds();
        }
        List<Integer> rank = ReptileUtil.getRank(workIdList);
        if(!RandTask.isSameAndPreRank(rank)){
            FileUtil.printFileString(RANKFILE, JSON.toJSONString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    + ":" + JSON.toJSONString(rank));
        }
        System.out.println("结束：" + JSON.toJSONString(preRank));
    }

    public static void main(String[] args){

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new RandTask(), 0, 1, TimeUnit.MINUTES);
    }
}
