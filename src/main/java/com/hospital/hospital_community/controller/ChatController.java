package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.entity.ChatMessage;
import com.hospital.hospital_community.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequestMapping("/api/v1/chat")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private final ChatService chatService;

    public record WriteMessageResponse(Long id){

    }
    public record WriteMessageRequest(String authorName, String content){

    }
    @PostMapping("/write-message")
    public RsData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest dto){
        ChatMessage message = new ChatMessage(dto.authorName(), dto.content());
//        chatMessageList.add(message);
        chatService.save(message);
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
//        List<ChatMessage> responseChatMessageList = chatMessageList;
        List<ChatMessage> chatMessages = chatService.findAll();
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
