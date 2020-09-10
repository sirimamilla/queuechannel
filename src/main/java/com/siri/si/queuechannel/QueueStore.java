package com.siri.si.queuechannel;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueueStore {

    Map<String, PollableChannel> queueMap=new HashMap<>();

    public void setChannel(String correlationId, PollableChannel channel){
        queueMap.put(correlationId, channel);
        System.out.printf("Queue size on add %d \n", queueMap.size());
    }

    public PollableChannel getChannel(String correlationId){
        return queueMap.get(correlationId);
    }


    public void remove(String id) {
        PollableChannel channel = queueMap.remove(id);
        if (channel instanceof  QueueChannel) {
            ((QueueChannel) channel).destroy();
        }
        System.out.printf("Queue size after remove %d \n", queueMap.size());
    }


}
