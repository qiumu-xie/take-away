package com.qiumu.common;

public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setEmpId(long id){
        threadLocal.set(id);
    }

    public static Long getEmpId(){
        return threadLocal.get();
    }
}
