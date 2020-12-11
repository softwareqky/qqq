/**
 * 
 */
package project.edge.domain.mobile.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angel_000
 *         API调用时，用来保存分页的数据返回值。
 */
public class PagedResultBean<T> {

    /** 总的记录数 */
    private long total;

    /** 当前页返回的记录数 */
    private int count;

    /** 当页的记录列表 */
    private List<T> rows = new ArrayList<T>();

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
