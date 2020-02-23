package com.sixsixsix516.sunjwt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sun 2020/2/23 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;

    private String message;

    private T data;


    public static <T> Result success(int code,String message, T... data) {
        return new Result(code,message,data);
    }

    public static <T> Result fail(int code,String message, T... data) {
        return new Result(code,message,data);
    }

}
