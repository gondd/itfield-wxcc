package cn.itfield.wxcc.result;

import lombok.Data;

/**
 * ajax请求的返回对象
 */
@Data
public class JsonResult {
    private Boolean success = true;
    private String message = "操作成功！！";
    private Object data;//操作之后返回的额外数据
    private Integer code = 200;

    public static JsonResult me(){
        return new JsonResult();
    }
    public static JsonResult error(String message){
        return new JsonResult().setSuccess(false).setMessage(message).setCode(5000);
    }

    /**
     * 链式编程思维
     * @param success
     * @return
     */
    public JsonResult setSuccess(Boolean success) {
        this.success = success;
        return this;//返回当前线程操作的对象
    }

    public JsonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }

    public JsonResult setCode(Integer code) {
        this.code = code;
        return this;
    }
}
