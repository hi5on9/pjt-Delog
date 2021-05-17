package com.web.blog.file;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {
	private int fid;
	private int pid;
	private String filename; // 저장할 파일 이름
	private String originalFilename; // 실제 파일 이름
	private String fileUrl; // 파일 경로
	private Date create_date;
}

