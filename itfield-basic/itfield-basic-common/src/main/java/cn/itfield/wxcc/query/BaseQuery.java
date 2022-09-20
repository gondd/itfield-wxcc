package cn.itfield.wxcc.query;

import lombok.Data;

@Data
public class BaseQuery {
    //当前页码
    private Integer pageNo = 1;
	//每页条数
    private Integer pageSize = 10;
	//默认以id字段排序
	private String orderField = "id";
	//默认降序
	private String orderType = "desc";
    //关键字高级查询
    private String keyword;

	/**
	 * 获取起始行号索引
	 * LIMIT ?,?
	 *		第一个整数是起始索引  通过 (当前页码-1)*每页条数 计算而来
	 *		第二个整数是每页条数
	 */
    public Integer getStart(){
        return (this.pageNo-1) * this.pageSize;
    }
}
