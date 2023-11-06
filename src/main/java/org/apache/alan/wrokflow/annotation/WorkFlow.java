package org.apache.alan.wrokflow.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkFlow {

    /**
     * 模块
     */
    String moduleValue() default "";

    /**
     * 业务模块数据id 如果不指定，不传值会根据返回值获取
     */
    String spelValue() default "";
}
