package zzvcom.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * To change this template use File | Settings | File Templates.
 */
public class ContentHelper {

    private static ClassPathXmlApplicationContext _ctx;

    static {
        _ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static ClassPathXmlApplicationContext getContext() {
        return _ctx;
    }
}
