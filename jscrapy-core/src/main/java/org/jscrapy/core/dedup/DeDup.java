package org.jscrapy.core.dedup;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * URL去重
 * Created by cxu on 2015/6/22.
 */
public abstract class DeDup extends JscrapyComponent {

    /**
     * 测试是否是重复的
     * @param request
     * @return
     */
    protected abstract boolean isDup(Request request);

    /**
     * 返回非重复的
     * @param request
     * @return
     */
    public List<HttpRequest> deDup(List<HttpRequest> request){
        List<HttpRequest> req = new ArrayList<>(request.size());
        for(HttpRequest url : request){
            if(!this.isDup(url)){
                req.add(url);
            }
        }
        return req;
    }
}
