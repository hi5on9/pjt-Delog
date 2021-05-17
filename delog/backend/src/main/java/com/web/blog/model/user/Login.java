package com.web.blog.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	
    private String email;
    private String password;
    
    
    
}
