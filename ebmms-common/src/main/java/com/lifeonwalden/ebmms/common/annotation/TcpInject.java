package com.lifeonwalden.ebmms.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcpInject {
    /**
     * Assign a version number to this tcp service
     * The method annotation is higher priority than type annotation
     *
     * @return
     */
    int version() default 0;

    /**
     * allowed maximum times of retry
     */
    int maxRetryTimes() default 1;

    /**
     * the service interface class
     *
     * @return
     */
    Class serviceInterface();

    /**
     * Description
     */
    String description() default "";
}
