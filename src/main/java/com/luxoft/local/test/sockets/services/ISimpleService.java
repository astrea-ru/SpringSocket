package com.luxoft.local.test.sockets.services;


public interface ISimpleService {

    /*мессагу, которую будем получать при запросе данных*/
    String getMessage();

    /*Должен быть реализован метод-планировщик, который с заданной периодичностью будет выполнять отправку собщений*/
    void sendResult();

}
