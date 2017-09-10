package com.lifeonwalden.ebmms.common.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * annotation for tcp service
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface TcpService {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

    /**
     * Assign a name to this tcp service
     * The method using is higher priority than the type using
     * <p>
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

    /**
     * Description
     */
    String description() default "";
}