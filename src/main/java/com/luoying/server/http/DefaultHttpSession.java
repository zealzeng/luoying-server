package com.luoying.server.http;

import com.luoying.server.ConcurrentSession;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultHttpSession extends ConcurrentSession implements HttpSession {

    /**
     * Close session
     */
    public void invalidate() {
        //TODO
    }
}
