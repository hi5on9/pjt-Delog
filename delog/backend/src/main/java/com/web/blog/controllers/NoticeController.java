package com.web.blog.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.Notice;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;
import com.web.blog.services.NoticeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/notice")
@RestController()
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@PostMapping("/")
	@ApiOperation(value = "Notice 등록")
	public Object notice(@Valid @RequestBody Notice request) {
		ResponseEntity response = null;

//		//System.out.println("Notice) /notice 요청] (Notice 등록) request : " + request);

		try {
			int cnt = noticeService.writeNotice(
					new Notice(0, request.getTitle(), request.getContents(), null, request.getIs_hidden(), 
							request.getIs_comment(), request.getIs_main(), request.getCategory(), 0));
			
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("/")
	@ApiOperation(value = "Post 수정")
	public Object update(@Valid @RequestBody Notice request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /notice 요청] (Notice 수정) request : " + request);
//		//System.out.println(request.getContents());
		try {
			int cnt = noticeService.modifyNotice(new Notice(request.getNid(), request.getTitle(), request.getContents(),
					request.getCreate_date(), request.getIs_hidden(), request.getIs_comment(),
					request.getIs_main(), request.getCategory(), request.getHit()));
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@DeleteMapping("/")
	@ApiOperation(value = "Notice 삭제")
	public Object delete(@RequestParam(required = true) final int nid) {
		ResponseEntity response = null;

//		//System.out.println("DELETE) /notice 요청] (Notice 삭제) nid : " + nid);

		try {
			int cnt = noticeService.deleteNotice(nid);
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/detail")
	@ApiOperation(value = "공지사항 조회")
	public Object detail(@RequestParam(required = true) final int nid) {
//		//System.out.println("GET) /notice/detail 요청] (공지사항 조회) nid : " + nid);

		ResponseEntity response = null;
		Notice selectNotice = null;
		try {
			selectNotice = noticeService.getNotice(nid);
//			//System.out.println("GET) /notice/detail 요청] selectNotice : " + selectNotice);

			if (selectNotice != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = selectNotice;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return response;
	}

	@GetMapping()
	@ApiOperation(value = "전체 조회")
	public Object allsearch(@RequestParam(required = true) final int is_hidden) {
//		//System.out.println("GET) /Notice 요청] (전체조회 )");
		List<Notice> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = noticeService.getNoticeList(is_hidden);
//			//System.out.println("GET) /notice 요청] (전체 조회 ) list : " + list);
			if (list != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = list;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	@GetMapping("/main")
	@ApiOperation(value = "main화면 공지사항")
	public Object mainsearch() {
//		//System.out.println("GET) /Notice 요청] (Main 공지사항 top5)");
		List<Notice> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = noticeService.getMain();
			
			if (list != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = list;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	
	@PutMapping("/mainchange")
	@ApiOperation(value = "main 변경")
	public Object mainchange(@Valid @RequestBody Notice request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /mainchange 요청] (main 변경) notice : " + request);

		try {
			int cnt = noticeService.changeMain(
					new Notice(request.getNid(), request.getTitle(), request.getContents(), null, request.getIs_hidden(), 
							request.getIs_comment(), request.getIs_main(), request.getCategory(), 0));
			
			
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
}