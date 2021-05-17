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
import com.web.blog.model.Note;
import com.web.blog.model.NoteUser;
import com.web.blog.model.Notice;
import com.web.blog.services.NoteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*")
@RequestMapping("/note")
@RestController()
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@PostMapping("")
	@ApiOperation(value = "Note 보내기")
	public Object notice(@Valid @RequestBody Note request) {
		ResponseEntity response = null;

//		//System.out.println("Note) /note 요청] (Note 보내기) request : " + request);

		try {
			int cnt = noteService.writeNote(
					new Note(0, request.getSender(), request.getRecipient(), request.getContents(), null, 0));
			
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
	
	@GetMapping()
	@ApiOperation(value = "전체 조회")
	public Object allsearch(@RequestParam(required = true) final int uid, 
			@RequestParam(required = true) final int type) {
//		//System.out.println("GET) /Note 요청] (전체조회 )");
		List<NoteUser> list = new ArrayList<>();
		ResponseEntity response = null;
		
		
		// type 1 : 모든 받은 쪽지 / type 2 : 모든 보낸 쪽지
		try {
			if(type == 1) 
				list = noteService.getSendList(uid);				
			else
				list = noteService.getReciveList(uid);
			
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
	
	
	@GetMapping("/detail")
	@ApiOperation(value = "쪽지 조회")
	public Object detail(@RequestParam(required = true) final int nid) {
		//System.out.println("GET) /note/detail 요청] (쪽지 조회) nid : " + nid);

		ResponseEntity response = null;
		NoteUser selectNote = null;
		
		// type 1 : 받은 쪽지 / type 2 : 보낸 쪽지
		
		try {
			
			selectNote = noteService.getNote(nid);
//			//System.out.println("GET) /notice/detail 요청] selectNote : " + selectNote);

			if (selectNote!= null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = selectNote;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return response;
	}
	
	
	@PutMapping("")
	@ApiOperation(value = "Note 쓰레기통")
	public Object update(@Valid @RequestBody int nid) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /note 요청] (note 쓰레기통 보내기) nid: " + nid);

		try {
			int cnt = noteService.trashNote(nid);
			
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

	@DeleteMapping("")
	@ApiOperation(value = "쪽지 삭제")
	public Object delete(@RequestParam(required = true) final int nid) {
		ResponseEntity response = null;

//		//System.out.println("DELETE) /note 요청] (note 삭제) nid : " + nid);

		try {
			int cnt = noteService.deleteNote(nid);
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