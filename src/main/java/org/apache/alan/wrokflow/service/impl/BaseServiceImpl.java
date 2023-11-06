package org.apache.alan.wrokflow.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.alan.wrokflow.dto.PageDto;
import org.apache.alan.wrokflow.dto.QueryDto;
import org.apache.alan.wrokflow.result.ResultQueryVo;
import org.apache.alan.wrokflow.service.BaseService;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.vo.PageVo;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.function.BiFunction;

public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected  <r, d> ResultQueryVo<r> pagingQuery(QueryDto dto, BiFunction<IPage<r>, d, IPage<r>> method){
        IPage<r> iPage = new Page<>(dto.getPage(),dto.getSize());
        d d = (d) dto;
        iPage = method.apply(iPage,d);
        return ResultQueryVo.getResultQueryVo(iPage);
    }

    protected  <r, d> PageVo<r> pagingQuery(PageDto dto, BiFunction<IPage<r>, d, IPage<r>> method){
        IPage<r> iPage = new Page<>(dto.getCurrentPage(),dto.getPageSize());
        d d = (d) dto;
        iPage = method.apply(iPage,d);
        return ResultQueryVo.getPageVo(iPage);
    }

    protected <V> PageVo<V> pagingQuery(PageDto pageDto,Class<V> destinationClass) {
        return pagingQuery(pageDto,destinationClass,null);
    }

    /**
     * 分页查询
     *
     * @param pageDto 分页信息
     * @param destinationClass 目标结果元素类型
     * @param queryWrapper 查询条件
     * @return 查询结果
     */
    protected <V> PageVo<V> pagingQuery(PageDto pageDto, Class<V> destinationClass, QueryWrapper<T> queryWrapper) {
        Page<T> page = convertPage(pageDto);
        //默认创建时间降序
//        boolean orderBy = queryWrapper.getTargetSql().toLowerCase(Locale.ROOT).contains("order by");
//        if(!orderBy){
//            queryWrapper.orderByDesc("create_time");
//        }
        Page<T> pageResult = page(page,queryWrapper);
        return PageVo.build(pageResult, MapperUtils.mapList(pageResult.getRecords(),destinationClass));
    }

    private Page<T> convertPage(PageDto pageDto) {
        Page<T> page = new Page<>();
        page.setSize(pageDto.getPageSize());
        page.setCurrent(pageDto.getCurrentPage());
        return page;
    }

    protected <V> PageVo<V> customPagingQuery(PageDto pageDto,QueryExecutor<M,T,V> queryExecutor) {
        Assert.notNull(pageDto, "pageDto cannot be null");
        Assert.notNull(queryExecutor, "queryExecutor cannot be null");
        Map<String, Object> condition = BeanUtil.beanToMap(pageDto);
        Page<T> page = convertPage(pageDto);
        Page<V> pageResult = queryExecutor.doQuery(baseMapper,page,condition);
        return PageVo.build(pageResult);
    }


    @FunctionalInterface
    public interface QueryExecutor<M extends BaseMapper,T,V> {
        Page<V> doQuery(M mapper, Page<T> page, Object condition);
    }

    protected void notNullLikeLeft(Object o,String column ,QueryWrapper<T> wrapper) {
        wrapper.likeLeft(o!=null,column,o);
    }

    protected void notNullLikeRight(Object o,String column ,QueryWrapper<T> wrapper) {
        wrapper.likeRight(o!=null,column,o);
    }


    protected void notNullLike(Object o,String column ,QueryWrapper<T> wrapper) {
        wrapper.like(o!=null,column,o);
    }

    protected void notNullEq(Object o,String column ,QueryWrapper<T> wrapper) {
        wrapper.eq(o!=null,column,o);
    }
}
