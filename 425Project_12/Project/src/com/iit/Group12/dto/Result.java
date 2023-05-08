package com.iit.Group12.dto;

/**
 * comm result object
 */
public class Result<T> {
    private boolean success;
    private T obj;

    public Result(boolean success, T obj) {
        this.success = success;
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
