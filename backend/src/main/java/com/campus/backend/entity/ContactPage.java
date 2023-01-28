package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class ContactPage extends Pages{
    private List<ContactItem> records;
}
