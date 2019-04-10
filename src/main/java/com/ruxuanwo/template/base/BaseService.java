package com.ruxuanwo.template.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 扩展Mybatis-Plus接口
 *
 * @author ruxuanwo
 */
public interface BaseService<T> extends IService<T> {
    List<T> find(T entity);
    IPage<T> findPage(IPage<T> page, T entity);
    T getOne(T entity);
//    T getById(Serializable id);
    boolean remove(T entity);
}
