package com.oxf1.myspider.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 代表了一个Queue里的url请求
 * Created by cxu on 2014/11/21.
 */
public abstract class Request implements Serializable{

    /**
     * 从队列里的json字符串来创建一个HttpRequest
     * @param jsonString
     * @return
     */
    public static Request build(String jsonString, Class clazz) throws JSONException {
        if(StringUtils.isNotBlank(jsonString)) {
            Request req = (Request)JSON.parseObject(jsonString, clazz);
            return req;
        }

        return null;
    }

    /**
     * 请求地址
     * @return
     */
    public abstract String getUrl();

    /**
     * 获取url的请求方法：GET|POST|DELETE|TRACE|HEAD..
     * @return
     */
    public abstract HttpRequestMethod getHttpMethod();

    /**
     * 如果是POST请求，获取请求的参数
     * @return
     */
    public abstract Map<String, String> getParameters();

    public abstract String asJson();

    /**
     * 对象的一个md5标识，用于去重
     * @return
     */
    public abstract String fp();

}
