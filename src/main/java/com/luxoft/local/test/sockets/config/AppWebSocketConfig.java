package com.luxoft.local.test.sockets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker //подключаем поддержку веб-сокетов
public class AppWebSocketConfig extends
        AbstractWebSocketMessageBrokerConfigurer /*Определяет методы для конфигурирования обработки сообщений с простыми протоколами обмена сообщениями (например, STOMP) от клиентов WebSocket.*/{

    @Override

    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*фильтр по которому случает результат клиент*/
        config.enableSimpleBroker(SocketAddresses.SOCKET_RESULT_POINT); //Включение простого брокера сообщений и настройка префиксов для фильтрации адресатов (например назначений с префиксом "/topic").
        /*фильтр по которому будут направлться сообщения от клиента*/
        config.setApplicationDestinationPrefixes(SocketAddresses.SOCKET_POINT); //устанавливаем точку на которой будут базироваться сокеты, т.е. указываем место для получения сообщений
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //определяем точку (точки), к которой (которым) будет подключен сокет клиента
        registry.addEndpoint(SocketAddresses.SOCKET_MESSAGE_REQUEST).withSockJS(); //не забываем указать, что работаем через SockJS, который позволяет упростить взаимодействие по протоколу STOMP
    }
}