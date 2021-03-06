package com.lifeonwalden.ebmms.common.annotation;

import java.lang.annotation.*;

/**
 * annotation for tcp service
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcpService {
    /**
     * the service interface class
     *
     * @return
     */
    Class serviceInterface();

    /**
     * Assign a version number to this tcp service
     * The method annotation is higher priority than type annotation
     *
     * @return
     */
    int version() default 0;

    /**
     * Description
     */
    String description() default "";
}