package com.eogo.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;



/**
 * 自定义的mapper，包含批量处理
 * @param <T>
 */
@RegisterMapper
public interface EgMapper<T> extends Mapper<T>, IdListMapper<T,Long>, InsertListMapper<T> {
}
