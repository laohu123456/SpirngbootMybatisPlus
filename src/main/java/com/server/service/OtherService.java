package com.server.service;

import java.util.Map;

public interface OtherService {

    public boolean saveTips(Integer expireTime);

    void sendMessage(Map<String,String> map);
}
