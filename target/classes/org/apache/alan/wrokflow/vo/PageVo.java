package org.apache.alan.wrokflow.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.alan.wrokflow.result.ResultQueryVo;
import org.apache.alan.wrokflow.utils.MapperUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2021-07-07 19:23
 */
@ApiModel
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {

    @ApiModelProperty(name = "total", value = "总记录数")
    @Builder.Default
    private long total = 0;

    @ApiModelProperty(name = "current_page", value = "当前页码")
    @Builder.Default
    private long currentPage = 1;

    @ApiModelProperty(name = "page_size", value = "页面大小")
    @Builder.Default
    private long pageSize = 30;

    @ApiModelProperty(name = "pages", value = "总页数")
    @Builder.Default
    private long pages = 0;

    @ApiModelProperty(name = "has_next", value = "是否有下一页")
    @Builder.Default
    private boolean hasNext = false;

    @ApiModelProperty(name = "has_previous", value = "是否有上一页")
    @Builder.Default
    private boolean hasPrevious = false;

    @ApiModelProperty(name = "list", value = "数据列表")
    @Builder.Default
    private List<T> list = new ArrayList<>();

    public PageVo(Page<T> data) {
        this.total = data.getTotal();
        this.currentPage = data.getCurrent();
        this.pageSize = data.getSize();
        this.pages = data.getPages();
        this.hasNext = this.currentPage < this.pages;
        this.hasPrevious = this.currentPage > 1;
        this.list = data.getRecords();
    }

    public PageVo(IPage<T> data) {
        this.total = data.getTotal();
        this.currentPage = data.getCurrent();
        this.pageSize = data.getSize();
        this.pages = data.getPages();
        this.hasNext = this.currentPage < this.pages;
        this.hasPrevious = this.currentPage > 1;
        this.list = data.getRecords();
    }

    public <T1> PageVo(IPage<T1> data, List<T> content) {
        this.total = data.getTotal();
        this.currentPage = data.getCurrent();
        this.pageSize = data.getSize();
        this.pages = data.getPages();
        this.hasNext = this.currentPage < this.pages;
        this.hasPrevious = this.currentPage > 1;
        this.list = content;
    }

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
        vo.setHasNext(iPage.getCurrent() < iPage.getSize());
        vo.setHasPrevious(iPage.getCurrent() > 1);
        return vo;
    }

    public static <c,t> PageVo<c> getPageVo(IPage<t> iPage, Class<c> clazz){
        List<t> records = iPage.getRecords();
        List<c> cs = MapperUtils.mapList(records, clazz);
        PageVo<c> vo = new PageVo<>();
        vo.setPages(iPage.getPages());
        vo.setList(cs);
        vo.setTotal(iPage.getTotal());
        vo.setCurrentPage(iPage.getCurrent());
        vo.setPageSize(iPage.getSize());
        vo.setHasNext(iPage.getCurrent() < iPage.getSize());
        vo.setHasPrevious(iPage.getCurrent() > 1);
        return vo;
    }

    public static <T> PageVo<T> build(Page<T> data) {
        return new PageVo<>(data);
    }

    public static <T> PageVo<T> build(IPage<T> data) {
        return new PageVo<>(data);
    }

    public static <T, R> PageVo<R> build(IPage<T> data, List<R> content) {
        return new PageVo<>(data, content);
    }
}
