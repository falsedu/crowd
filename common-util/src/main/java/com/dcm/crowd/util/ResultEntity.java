package com.dcm.crowd.util;

public class ResultEntity<T> {

    public static final String SUCCESS="SUCCESS";
    public static final String FAILED="FAILED";

    //用来封装当前请求处理的结果是失败还是成功
    private String result;

    //处理失败时返回的错误信息
    private String message;

    //要返回的数据
    private T data;

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResultEntity() {
    }

    /**
     * 请求处理成功且不需要返回数据时使用的工具方法。
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 请求处理成功且需要返回数据时使用的工具方法。
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(SUCCESS,null,data);
    }
    /**
     * 请求处理失败且需要返回失败信息时使用的工具方法。
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message){
        return new ResultEntity<Type>(FAILED,message,null);
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setData(T data) {
        this.data = data;
    }
}
