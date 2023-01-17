package com.campus.backend.entity;

import lombok.Data;

@Data
public class PostItem extends Post{
    private String universityName;
    private String userName;
    private boolean likeFlag=false;


    public PostItem(Post post)
    {
        super(post);
    }

}
