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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.Prison;
import com.web.blog.model.Report;
import com.web.blog.model.ReportList;
import com.web.blog.model.ReportUser;
import com.web.blog.model.user.User;
import com.web.blog.service.user.UserService;
import com.web.blog.services.ReportService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/report")
@RestController()
public class ReportController {

	@Autowired
	ReportService reportService;
	
	@Autowired
	UserService userService;
		
	@GetMapping()
	@ApiOperation(value = "전체 조회")
	public Object allsearch(@RequestParam(required = true) final int uid) {
		//System.out.println("GET) /report 요청] (신고내역 조회 )");
		List<ReportUser> list = new ArrayList<>();
		ResponseEntity response = null;
		
		
		try {
			list = reportService.getReporterList(uid);
			
			//System.out.println("GET) /report 요청] (신고내역 조회 ) list : " + list);
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
	
	
	@PostMapping("")
	@ApiOperation(value = "신고하기")
	public Object sendreport(@Valid @RequestBody Report request) {
		ResponseEntity response = null;

//		System.out.println("Report) /report 요청] (신고하기) request : " + request);

		try {
			int cnt = reportService.sendReport(new Report(0, request.getReporterUid(), request.getReportedUid(),
					request.getReason(), null)); 
			System.out.println("cnt : " + cnt);
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
	
	@PostMapping("/check")
	@ApiOperation(value = "신고중인지 확인")
	public Object check(@Valid @RequestBody Report request) {
		ResponseEntity response = null;
		//System.out.println("Report) /report 요청] (신고중인지 확인) request : " + request);
		
		try {
			List<Report> report = reportService.getReport(new Report(0, request.getReporterUid(), request.getReportedUid(), null, null));
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (report != null) {
				result.object = true;
			} else {
				result.object = false;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "전체 조회")
	public Object allsearch() {
		//System.out.println("GET) /report 요청] (관리자 : 신고내역 조회 )");
		List<ReportList> list = new ArrayList<>();
		ResponseEntity response = null;
		
		
		try {
			list = reportService.getAllReportList();
			
			//System.out.println("GET) /report 요청] (관리자 : 신고내역 조회 ) list : " + list);
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
	
	// 유저 신고 취소 + 운영자 신고 거절
	
	@DeleteMapping()
	@ApiOperation(value = "Report에서 삭제")
	public Object deletereport(@RequestParam(required = true) final int rid) {
		
		ResponseEntity response = null;

		//System.out.println("DELETE) /report 요청] (report 삭제) rid : " + rid);

		try {
			int cnt = reportService.deleteReport(rid);
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
	
	
	@PostMapping("/prison")
	@ApiOperation(value = "신고 승인")
	public Object sendprison(@Valid @RequestBody ReportList request) {
		ResponseEntity response = null;

//		System.out.println("Report) /report 요청] (신고 승인) request : " + request);
		int cnt = 0;
		try {
			
			
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			
			cnt = reportService.sendPrison(new Prison(0, request.getReportedUid(), request.getReason(), null, 10));
			reportService.deleteReport(request.getRid());
			//System.out.println("hi");
			if(cnt == 1) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				result.object = true;
			}
			else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	@GetMapping("/release")
	@ApiOperation(value = "강금 해제")
	public Object release() {
		ResponseEntity response = null;
		
		try {
			int cnt = reportService.deletePrison();
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		
		return response;
	}
	
	
	@PostMapping("/check/prison")
	@ApiOperation(value = "신고중인지 확인")
	public Object checkprison(@Valid @RequestBody ReportList request) {
		ResponseEntity response = null;
		
		
		//System.out.println("uid -> " + request.getReportedUid());
		//System.out.println(request.getReporterUid() + " " + request.getReportedUid());
		try {
			Prison prison = null;
			
			prison = reportService.checkPrison(request.getReportedUid());				
			
			List<Report> report = reportService.getReport(new Report(request.getReporterUid(), request.getReportedUid()));
			//System.out.println("prison -> " + prison);
			//System.out.println("report-> " + report);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if(prison == null) {
				result.object = report;
			}else {
				result.object = null;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	@DeleteMapping("/check")
	@ApiOperation(value = "신고중인지 확인")
	public Object prison() {
		ResponseEntity response = null;
		
		
		try {
			
			int cnt = reportService.deletePrison();
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@PostMapping("/check/userprison")
	@ApiOperation(value = "내가 신고중인지 보기")
	public Object checkuserprison(@Valid @RequestBody User request) {
		ResponseEntity response = null;
		
		
		
		try {
			Prison prison = null;
			User info = userService.getUserByEmail(request.getEmail());
			prison = reportService.checkPrison(info.getUid());				
			
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = prison;
			
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

}