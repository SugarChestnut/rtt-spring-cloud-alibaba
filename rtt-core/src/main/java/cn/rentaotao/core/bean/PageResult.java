package cn.rentaotao.core.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 统一的分页返回对象
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long total;        //总记录数
    private List<T> records;    //结果集
    private long num;    // 第几页
    private long size;    // 每页记录数
    private long pages;        // 总页数
    private long numberOfRecords;        // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性


    public PageResult() {

    }

    /**
     * 处理List对象包装成分页信息返回
     *
     * @param list 不分页时的查询结果
     */
    public PageResult(List<T> list) {
        if (list != null) {
            this.num = 1; // 当前页, 这里要注意下，jpa分页是从第0页开始的，mybatis从第1页开始，现在这个信息前端不会用到，会在查询体现
            this.size = list.size(); // 每页的数量
            this.total = list.size(); // 总记录数
            this.pages = 1; // 总页数
            this.records = list; // 结果集
            this.numberOfRecords = list.size(); // 当前页的数量
        }
    }

    /**
     * 处理Mybatis plus分页查询结果包装成分页信息返回
     *
     * @param page 分页查询结果
     */
    public PageResult(IPage<T> page) {
        if (page != null) {
            this.num = page.getCurrent();
            this.size = page.getSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.records = page.getRecords();
            if (this.records != null && !this.records.isEmpty()) {
                this.numberOfRecords = this.records.size();
            } else {
                this.numberOfRecords = 0L;
            }
        }
    }
}