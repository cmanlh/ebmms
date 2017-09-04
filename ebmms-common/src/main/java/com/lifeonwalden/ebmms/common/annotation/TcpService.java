package com.lifeonwalden.ebmms.common.annotation;

import java.lang.annotation.*;

/**
 * annotation for tcp service
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcpService {
    /**
     * Assign a name to this tcp service
     * The method using is higher priority than the type using
     *
     * Type annotated as A.B, method annotated as C, the full name is A.B.C
     * Type without annotation, and method annotated as C, the full name is C
     *
     * @return
     */
    String name() default "";

    /**
     * Assign a version number to this tcp service
     * The method annotation is higher priority than type annotation
     *
     * @return
     */
    int version() default 0;
}