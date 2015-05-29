package com.luxoft.local.test.sockets.services;

import com.luxoft.local.test.sockets.config.SocketAddresses;
import com.luxoft.local.test.sockets.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimpleService implements ISimpleService/*, ApplicationListener<BrokerAvailabilityEvent>*/ {

    @Override
    public String getMessage() {
        return "It does not mean anything =)";
    }

    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public SimpleService(
            final MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendResult() {
        Random random = new Random();
        this.messagingTemplate.convertAndSend(
                SocketAddresses.SOCKET_SEND_TO, new Result(String.format("New int: %d", random.nextInt(10))));

    }


   /* @Override
    public void onApplicationEvent(BrokerAvailabilityEvent brokerAvailabilityEvent) {
    }*/
}
