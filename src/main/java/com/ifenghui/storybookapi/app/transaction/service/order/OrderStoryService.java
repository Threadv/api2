package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.user.entity.User;

import java.util.List;

public interface OrderStoryService {
    void addOrderStoryList(List<Long> storyIds, User user, Long payStoryOrderId);
}
