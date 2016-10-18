package per.refresh.run;

import per.refresh.util.ClientProperties;
import per.refresh.util.HTHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/10.
 */
public class RefreshTask {
    private static List<String> refreshTimeList = new ArrayList();
    private static List<String> workIdList = new ArrayList();
    private static String username;
    private static String password;

    private void initConfig()
    {
        username = ClientProperties.getProperty("username");
        password = ClientProperties.getProperty("password");
        string2List(ClientProperties.getProperty("workIds"), workIdList, 10);
        string2List(ClientProperties.getProperty("refreshTimes"), refreshTimeList, 3);
    }

    private void string2List(String strs, List<String> list, int maxSize)
    {
        if (strs != null)
        {
            strs = strs.trim();
            String[] arry = strs.split(",");
            for (String temp : arry)
            {
                temp = temp.trim();
                if ((!"".equals(temp)) && (!list.contains(temp)) && (list.size() < maxSize))
                {
                    /*list.add(temp);*/
                }
            }
        }
    }

    private void doAutoRefresh()
    {
        if (refreshTimeList.size() > 0)
        {
            Timer timer = new Timer();

            long daySpan = 86400000L;

            Date refreshTime = null;
            SimpleDateFormat sdf = null;
            Date currentDate = new Date();

            System.out.println("Main Tast Start!");
            for (String tempTime : refreshTimeList)
            {
                sdf = new SimpleDateFormat("yyyy-MM-dd " + tempTime);
                try
                {
                    refreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(currentDate));
                    System.out.println(">>>PlanTime: " + refreshTime);

                    if (System.currentTimeMillis() > refreshTime.getTime())
                    {
                        refreshTime = new Date(refreshTime.getTime() + daySpan);
                        System.out.println("The current time great than " + tempTime + ", it is runing at the next day.");
                    }

                    HTHelper.login(username, password);

                    timer.schedule(new RemainTask(), refreshTime, daySpan);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("Please configuration the parameter value of refreshTime and restart.");
            System.exit(0);
        }
    }

    public RefreshTask()
    {
        initConfig();
        doAutoRefresh();
    }

    public static void main(String[] args)
    {
        new RefreshTask();
    }

    class RemainTask extends TimerTask
    {
        RemainTask()
        {
        }

        public void run()
        {
            if (RefreshTask.workIdList.size() > 0)
            {
                System.out.println(">>>ExecTime: " + new Date());
                HTHelper.sendHttp(RefreshTask.username, RefreshTask.password, RefreshTask.workIdList);
            }
            else
            {
                System.out.println("Please configuration the parameter value of workIds and restart.");
                System.exit(0);
            }
        }
    }
}
