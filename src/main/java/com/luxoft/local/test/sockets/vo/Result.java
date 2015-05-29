package com.luxoft.local.test.sockets.vo;

/**
 * Объект-результат, который выдает сокет
 */
public class Result {
    private String result;
    public Result(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }
}