package org.jscrapy.core.config;

import org.jscrapy.core.exception.MySpiderFetalException;

/**
 * Created by cxu on 2015/6/21.
 */
public interface ConfigOperator {
    public Object loadValue(String key);
    public void put(String key, Object value) throws MySpiderFetalException;

    /**
     * 配置文件的磁盘镜像位置
     * @param cfgSavePath
     */
    public void setPersistencePath(String cfgSavePath);
}