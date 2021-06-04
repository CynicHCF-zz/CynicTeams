package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.chat.enums.ChatMode;
import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ChatModeMap extends PersistMap<ChatMode> {

    public ChatModeMap() {
        super("ChatModes", "ChatMode");
    }


    public String getRedisValue(ChatMode chatMode) {
        return (chatMode.name());
    }


    public ChatMode getJavaObject(String str) {
        return (ChatMode.valueOf(str));
    }


    public Object getMongoValue(ChatMode chatMode) {
        return (chatMode.name());
    }

    public ChatMode getChatMode(UUID check) {
        return (contains(check) ? getValue(check) : ChatMode.PUBLIC);
    }

    public void setChatMode(UUID update, ChatMode chatMode) {
        updateValueAsync(update, chatMode);
    }

}