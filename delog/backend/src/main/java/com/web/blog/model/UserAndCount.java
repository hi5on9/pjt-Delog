package com.web.blog.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAndCount {

    private int uid;
    private String email;
    @JsonIgnore
    private String password;
    private String nickname;
    private int role = 0;
    private String provider;
    private String snsid;
    private Date create_date;
	private int count;
	private String profile;
}
