package com.ruxuanwo.template.redis;

/**
 * <pre>
 * 	用于当redis为的值为空时,执行数据库查询
 * </pre>
 * @author ruxuanwo
 *
 */
public interface INullTarget {

    /**
     *
     * @param condition 方法认为所有数据库查询所需的条件可以从该参数解析
     * @return
     */
    Object invoke(String condition) ;
}
