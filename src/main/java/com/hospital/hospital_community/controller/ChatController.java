package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.SseEmitters;
import com.hospital.hospital_community.domain.entity.ChatMessage;
import com.hospital.hospital_community.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/chat")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final SseEmitters sseEmitters;
    private final ChatService chatService;

    public record WriteMessageResponse(Long id){

    }
    public record WriteMessageRequest(String authorName, String content){

    }
    @GetMapping("/room")
    public String showRoom(){
        return "chat/room";
    }

    @PostMapping("/write-message")
    public RsData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest dto){
        ChatMessage message = new ChatMessage(dto.authorName(), dto.content());
        Long savedId = chatService.save(message);

        // SSE연결을 통해 생성되어 리스트로 emitter들에게 방생한 eventName을 알려준다(noti)
        sseEmitters.noti("chat__messageAdded");

        log.info("saved message id = " + savedId);
        return new RsData(
                "S-1",
                "success write message",
                new WriteMessageResponse(message.getId())
        );
    }
    public record MessageRequest(Long fromId){

    }
    public record MessageResponse(List<ChatMessage> chatMessages, int count){

    }

    @GetMapping("/messages") // ?fromId={fromId}
    public RsData<MessageResponse> messages(MessageRequest dto){
        List<ChatMessage> responseChatMessageList = chatService.findAll();

        // 번호가 입력되었다면
        if (dto.fromId != null){
            Long index = responseChatMessageList
                    .stream()
                    .map(chmesg2 -> chmesg2.getId())
                    .filter(chid -> chid == dto.fromId)
                    .findFirst()
                    .orElse(-1L);

            if(index != -1){
                // 만약에 index가 있다면, 0부터 index 번까지 제외한 리스트를 만든다.
                responseChatMessageList = responseChatMessageList.subList(index.intValue(), responseChatMessageList.size());
            }
        }
        return new RsData<>(
                "S-1",
                "success select message fromId",
                new MessageResponse(responseChatMessageList, responseChatMessageList.size())
        );

    }
}
