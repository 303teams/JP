package com.bjtu.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RspObject<T> {

    private int code;
    private String msg;
    private T data;

    private RspObject(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RspObject<T> success(String msg,T data){
        return new RspObject<T>(200,msg,data);
    }

    public static <T> RspObject<T> success(String msg){
        return success(msg,null);
    }


    public static RspObject success(){
        return success(null);
    }

    public static <T> RspObject<T> fail(String msg,T data){
        return new RspObject(500,msg,data);
    }

    public static RspObject fail(String msg){
        return fail(msg,null);
    }

    public static RspObject fail(){
        return fail("fail");
    }

    public static RspObject fail(int code,String msg){
        return new RspObject(code,msg,null);
    }

    public static void main(String[] args) {
//        System.out.println(success(new User("lzc","11","男","")));
    }

//    public static RspObject<List<String>> success(String msg, List<Student> all) {
//
//    }
}
