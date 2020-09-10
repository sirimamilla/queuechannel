package com.siri.si.queuechannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestService {
    @Autowired
    QueueStore queueStore;

    @GetMapping("/object/{id}")
    public String get(@PathVariable("id") String id){
        return getString(id);
    }

    private String getString(@PathVariable("id") String id) {
        PollableChannel channel=new QueueChannel(1);
        queueStore.setChannel(id, channel);
        try {
            return (String) channel.receive(20000).getPayload();
        }finally {
            queueStore.remove(id);


        }
    }

    @PostMapping("/object/{id}")
    public String post(@PathVariable("id") String id, @RequestBody String body){
        PollableChannel channel = queueStore.getChannel(id);
        channel.send(new GenericMessage<>(body));
        return "success";
    }
}
