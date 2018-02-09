package org.jscrapy.core.config;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统常量
 * Created by cxu on 2015/9/18.
 */
public class SysDefaultConfig {
    public static final String APP_NAME = "jscrapy";
    public static final String FILE_PATH_SEPERATOR = File.separator;
    public static final String  DEFAULT_SPIDER_WORK_DIR = System.getProperty("user.home")
            + FILE_PATH_SEPERATOR
            + "." + APP_NAME
            + FILE_PATH_SEPERATOR;

    public static String HOST;//本机的IP
    public static int SCHEDULER_BATCH_SIZE = 1;//默认每次从队列里拿出来多少url
    public static int THREAD_COUNT = 1;// 任务的默认线程数目
    public static int WAIT_URL_SLEEP_TIME_MS = 1000;


    static{
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            HOST = "?.?.?.?";
            //TODO log error
        }
        if(StringUtils.isBlank(HOST)){
            HOST = addr.getHostAddress().toString();
        }
    }
}
