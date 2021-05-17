package com.web.blog.model.user;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class ModifyInfo {
    
    @ApiModelProperty(required = true)
    String nickname;

    @ApiModelProperty(required = true)
//    @NotNull
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    // 최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 :
    String password;
    
    int type;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public int getType() {
       return type;
    }
    
    public void setType(int Type) {
       this.type = Type;
    }


}