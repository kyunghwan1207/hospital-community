package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.entity.ChatMessage;
import com.hospital.hospital_community.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Long save(ChatMessage chatMessage){
        ChatMessage savedChatMessage =  chatRepository.save(chatMessage);
        return savedChatMessage.getId();
    }
    public List<ChatMessage> findAll(){
        return chatRepository.findAll();
    }

}
