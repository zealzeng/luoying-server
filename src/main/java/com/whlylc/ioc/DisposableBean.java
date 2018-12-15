package com.whlylc.ioc;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
