package com.luxoft.local.test.sockets.controller;


import com.luxoft.local.test.sockets.config.SocketAddresses;
import com.luxoft.local.test.sockets.services.ISimpleService;
import com.luxoft.local.test.sockets.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
/*Объявляем контроллер для сокета*/
public class WebSocketController {

    @Autowired
    ISimpleService simpleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return "index";
    }

    /**
     * Метод для обработки сообщений, поступающих по сокету от клиента
     *
     * @return
     */
    @MessageMapping(SocketAddresses.SOCKET_MESSAGE_REQUEST) // расширение точки доступа сокета для считывания сообщений
    @SendTo(SocketAddresses.SOCKET_SEND_TO) //куда отправлем результат по сокету
    public Result messagesFromServer() {
        return new Result(simpleService.getMessage());
    }
}