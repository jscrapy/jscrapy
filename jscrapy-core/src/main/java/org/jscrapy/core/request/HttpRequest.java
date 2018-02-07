package org.jscrapy.core.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jscrapy.common.http.HttpHeaderConstant;
import org.jscrapy.core.proxy.WatchableSpiderProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpRequest extends Request{
    final static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String url;//请求的url
    private HttpRequestMethod httpMethod;//请求的http方法，GET|POST等
    private Map<String, String> parameters;//如果是post请求，这里存放请求参数

    private WatchableSpiderProxy proxy;
    private Map<String,String> header = new HashMap<String, String>();
    private List<Integer> acceptCode =   new ArrayList<Integer>();;
    private String charset;//站点的编码

    /**
     * 构造函数
     * @param url
     * @param httpMethod
     * @param parameters
     */
    public HttpRequest(String url, HttpRequestMethod httpMethod, Map<String, String> parameters) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.parameters = parameters;

        header.put(HttpHeaderConstant.USER_AGENT, "myspider@github");
        acceptCode.add(200);
    }

    public HttpRequest(String url) {
        this.url = url;
        this.httpMethod = HttpRequestMethod.GET;
    }

    public HttpRequest(){}

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public HttpRequestMethod getHttpMethod() {
        return this.httpMethod;
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public String asJson() {
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("url", this.url);
        jsonObject.put("http_method", this.httpMethod.name());

        /*Map里的key一定要排序之后，因为去重用的是md5*/
        if (parameters != null && !parameters.isEmpty()) {
            Map<String, Object> jsonParam = new TreeMap<String, Object>();
            Set<Map.Entry<String, String>> entrySet = this.parameters.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                jsonParam.put(entry.getKey(), entry.getValue());
            }
            JSONObject params = new JSONObject(jsonParam);
            jsonObject.put("parameters", params);
        }

        return jsonObject.toJSONString();
    }

    @Override
    public String uniqId() {
        String s = this.asJson();
        return DigestUtils.sha1Hex(s);
    }

    @Override
    public String toString(){
        return this.asJson();
    }

    @JSONField(name="url")
    public void setUrl(String url){
        this.url = url;
    }

    @JSONField(name="http_method")
    public void setHttpMethod(String method)
    {
        this.httpMethod = HttpRequestMethod.valueOf(method);
    }

    @JSONField(name="parameters")
    public void SetFormParameters(Map<String,String> params){
        this.parameters = params;
    }

    /**
     * 从队列里的json字符串来创建一个HttpRequest
     * @param jsonString
     * @return
     */
    public static HttpRequest build(String jsonString) throws JSONException {
        if(StringUtils.isNotBlank(jsonString)) {
            HttpRequest req = (HttpRequest) JSON.parseObject(jsonString, HttpRequest.class);
            return req;
        }

        return null;
    }
}
