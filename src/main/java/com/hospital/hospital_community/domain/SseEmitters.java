package com.hospital.hospital_community.domain;

import com.hospital.hospital_community.domain.utils.Ut;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SseEmitters {
    private final List<SseEmitter> emittersList = new CopyOnWriteArrayList<>();
    public SseEmitter add(SseEmitter emitter){
        this.emittersList.add(emitter);
        emitter.onCompletion(() -> {
            this.emittersList.remove(emitter);
        });
        emitter.onTimeout(() -> {
            emitter.complete();
        });
        return emitter;
    }
    public void noti(String eventName){
        noti(eventName, Ut.mapOf());
    }
    public void noti(String eventName, Map<String, Object> data){
        emittersList.forEach(emitter -> {
           try{
               emitter.send(
                       SseEmitter.event()
                               .name(eventName)
                               .data(data)
               );
           } catch (ClientAbortException e){

           } catch (IOException e){
               throw new RuntimeException(e);
           }
        });
    }
}
