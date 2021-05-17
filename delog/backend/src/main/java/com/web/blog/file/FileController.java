package com.web.blog.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.jwt.JwtService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.User;
import com.web.blog.service.user.UserService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/file")
@RestController()
public class FileController {

	@Value("${file.profile.upload.directory}")
	String uploadFileDir;

	@Value("{file.profile.path}")
	String profilePath;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	FileService fileService;
	
	@PostMapping("/profile/upload")
	public String upload(HttpServletRequest httpRequest, @RequestParam("file") MultipartFile files) {
		log.info("### upload");

		//System.out.println("/profile/upload : multipartFile files" + files);

//		String pathName = "C:\\ssafy\\images\\";

		// 1. jWT로 유저 정보
		User userOpt;

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		Map<String, Object> map = jwtService.get(httpRequest.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
		//System.out.println("uid : " + uid);
		String fileName = files.getOriginalFilename();
		String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

		String uploadFileDirTemp = uploadFileDir + "profile/"+ uid + "/";

		File destinationFile;
		String destinationFileName;
//
		do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
			destinationFile = new File(uploadFileDirTemp + destinationFileName);
		} while (destinationFile.exists());

		//System.out.println("filename : "  + fileName);
		//System.out.println("filenameExtension : "  + fileNameExtension);
		//System.out.println("destinationFileName : " + destinationFileName);

		//System.out.println("uploadFileDirTemp : " + uploadFileDirTemp);
		
		destinationFile.getParentFile().mkdirs();
		try {
			InputStream fileStream = files.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, destinationFile);
				
				// db에 저장
				try {
					int cnt = fileService.updateProfile(new UserProfile(uid, "/image/profile/" + uid + "/" + destinationFileName));
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";

	} // end upload
	
	
	@PostMapping("/thumbnail/upload")
	public String uploadthumbnail(@RequestParam(required = true) final int pid, @RequestParam("file") MultipartFile files) {
		log.info("### upload");

		//System.out.println("/thumbnail/upload : multipartFile files" + files);

//		String pathName = "C:\\ssafy\\images\\";

		// 1. jWT로 유저 정보

		//System.out.println("pid : " + pid);
		String fileName = files.getOriginalFilename();
		String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

		String uploadFileDirTemp = uploadFileDir + pid + "/";

		File destinationFile;
		String destinationFileName;
//
		do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
			destinationFile = new File(uploadFileDirTemp + destinationFileName);
		} while (destinationFile.exists());

		//System.out.println("filename : "  + fileName);
		//System.out.println("filenameExtension : "  + fileNameExtension);
		//System.out.println("destinationFileName : " + destinationFileName);

		//System.out.println("uploadFileDirTemp : " + uploadFileDirTemp);
		
		destinationFile.getParentFile().mkdirs();
		try {
			InputStream fileStream = files.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, destinationFile);
				
				// db에 저장
				try {
					int cnt = fileService.updateThumbnail(new PostThumbnail(pid, "/image/thumbnail/" + pid + "/" + destinationFileName));
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";

	} // end upload
}