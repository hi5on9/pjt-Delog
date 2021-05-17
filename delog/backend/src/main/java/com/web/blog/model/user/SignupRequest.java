package com.web.blog.model.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	String email;
	String password;
	@ApiModelProperty(required = true)
	@NotNull
	String nickname;
	String provider;
	String snsid;
	String profile;

}
