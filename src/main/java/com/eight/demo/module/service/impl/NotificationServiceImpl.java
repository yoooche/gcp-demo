package com.eight.demo.module.service.impl;

import org.springframework.stereotype.Service;

import com.eight.demo.module.service.NotificationService;
import com.eight.demo.module.to.NotificationTO;
import com.eight.demo.module.webSocket.SessionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SessionManager sessionManager;

    @Override
    public void pushToUsers(NotificationTO notification) {
        var userIds = notification.getUserIds();
        var success = 0;
        var total = userIds.size();

        for (var userId : userIds) {
            if (sessionManager.sendMessage(userId, notification.getTitle() + notification.getContent())) {
                success++;
            }
        }

        log.info("Push result: {}/{} users received notification", success, total);
    }

}
