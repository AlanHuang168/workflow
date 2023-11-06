package org.apache.alan.wrokflow.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.alan.wrokflow.vo.PageVo;

import java.util.List;

/**
 * 分页查询结果
 */
@Data
@ApiModel
public class ResultQueryVo<T> {

    @ApiModelProperty("当前页")
    private Integer page;

    @ApiModelProperty("页面数量")
    private Integer size;

    @ApiModelProperty("总数量")
    private Long total;

    @ApiModelProperty("总页数")
    private Long pages;

    @ApiModelProperty("数据")
    private List<T> data;

    public static <t> ResultQueryVo<t> getResultQueryVo(IPage<t> iPage){
        ResultQueryVo<t> vo = new ResultQueryVo<>();
        vo.setData(iPage.getRecords());
        vo.setPage((int) iPage.getCurrent());
        vo.setTotal(iPage.getTotal());
        vo.setPages(iPage.getPages());
        vo.setSize((int) iPage.getSize());
        return vo;
    }
    public static <t> PageVo<t> getPageVo(IPage<t> iPage){
        PageVo<t> vo = new PageVo<>();
        vo.setPages(iPage.getPages());
        vo.setList(iPage.getRecords());
        vo.setTotal(iPage.getTotal());
        vo.setCurrentPage(iPage.getCurrent());
        vo.setPageSize(iPage.getSize());
        vo.setHasNext(iPage.getCurrent()<iPage.getPages());
        vo.setHasPrevious(iPage.getCurrent() > 1);
        return vo;
    }



    public static <t> ResultQueryVo<t> getEmptyVo(){
        ResultQueryVo<t> vo = new ResultQueryVo<>();
        vo.setData(null);
        vo.setPage(1);
        vo.setTotal(0L);
        vo.setPages(1L);
        vo.setSize(10);
        return vo;
    }
}
