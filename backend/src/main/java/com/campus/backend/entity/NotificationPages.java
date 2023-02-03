package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data

public class NotificationPages extends Pages{
    private List<NotificationItem> records;
}
