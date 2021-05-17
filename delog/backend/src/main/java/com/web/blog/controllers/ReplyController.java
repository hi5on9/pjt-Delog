package com.web.blog.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.web.blog.jwt.JwtService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.Reply;
import com.web.blog.model.ReplyList;
import com.web.blog.model.ReplyUser;
import com.web.blog.model.post.LikeList;
import com.web.blog.services.ReplyService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/post/reply")
@RestController()
public class ReplyController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	ReplyService replyService;
	
	
	@PostMapping()
	@ApiOperation(value = "댓글 등록")
	public Object replyWrite(HttpServletRequest req, @Valid @RequestBody Reply reply){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		ResponseEntity response = null;

		Map<String, Object> map = jwtService.get(req.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int writer = (int) user.get("uid");
		
		try {
			int cnt = replyService.writeReply(new Reply(reply.getPid(), reply.getPrid(), writer, reply.getContents()));
//			//System.out.println("GET) /reply 등록] (reply 등록 ) cnt : " + cnt);
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = true;
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
	
	@GetMapping()
	@ApiOperation(value = "게시판 댓글 조회")
	public Object replySearch(@RequestParam(required = true) final int pid,
			@RequestParam(required = true) final int type) {
//		//System.out.println("GET) /reply 요청] (게시판 댓글 조회 )");
		List<ReplyUser> list = new ArrayList<>();
		ResponseEntity response = null;	

		try {
			list = replyService.getReplyList(new Reply(pid, type));
//			//System.out.println("GET) /reply 요청] (게시판 댓글 조회 ) list : " + list);
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
	
	@GetMapping("/re")
	@ApiOperation(value = "대댓글 조회")
	public Object rereplySearch(@RequestParam(required = true) final int pid,
			@RequestParam(required = true) final int prid, @RequestParam(required = true) final int type) {
//		//System.out.println("GET) /reply 요청] (대댓글 조회 )");
		List<ReplyUser> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = replyService.getReReplyList(new Reply(pid, prid, type));
//			//System.out.println("GET) /reply 요청] (대댓글 조회 ) list : " + list);
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
	
	
	//내가 맞는지 확인하기
	@GetMapping("/check")
	@ApiOperation(value = "자기 댓글인지 확인")
	public Object checkreply(HttpServletRequest req, @Valid @RequestBody Integer rid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		ResponseEntity response = null;

		Map<String, Object> map = jwtService.get(req.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
		
		try {
			Reply r = replyService.getReply(rid);
			
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if(r.getWriter() == uid) {
				result.object = true;
			}else
				result.object = false;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	@PutMapping("")
	@ApiOperation(value = "댓글 수정")
	public Object update(@Valid @RequestBody Map<String, Object> request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /reply 요청] (Reply 수정) rid : " + (Integer)request.get("rid"));

		try {
			Reply reply = replyService.getReply((Integer)request.get("rid"));
			reply.setContents((String)request.get("contents"));
			int cnt = replyService.modifyReply(reply);
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = reply;
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
	@ApiOperation(value = "댓글 삭제")
	public Object delete(@RequestParam(required = true) final int rid) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /reply 요청] (Reply 삭제) rid : " + rid);

		try {
			int cnt = replyService.deleteReply(rid);
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
	
	
	@PostMapping("/btn")
	@ApiOperation(value = "좋아요/싫어요")
	public Object btn(HttpServletRequest req, @Valid @RequestBody Reply reply) {
		ResponseEntity response = null;
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		Map<String, Object> map = jwtService.get(req.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
		
		LikeList list = new LikeList(reply.getRid(), uid);
		try {
			switch(reply.getType()) {
			case 1 : 
				replyService.likeReply(list);				
				break;
			case 2:
				replyService.hateReply(list);					
				break;
			case 3:
				replyService.unlikeReply(list);
				break;
			case 4:
				replyService.unhateReply(list);
			}
	
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		       
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	 @GetMapping("/listcheck")
	    @ApiOperation(value = "좋아요/싫어요 확인")
	    public Object listcheck(@RequestParam(required = true) final int uid,
	    		@RequestParam(required = true) final int rid, @RequestParam(required = true) final int type) {
	        
	        
	        ResponseEntity response = null;
	       
			
	        // type 1 : uid가 rid글 좋아요 누른적이 있나
	        // type 2 : uid가 rid글 싫어요 누른적이 있나
	        // result.object가 true면 누른적 있다. / false면 누른적 없다.
//	        //System.out.println("rid = " + rid + ", uid =  " + uid);
	        LikeList likelist = new LikeList(rid, uid);
	        LikeList info = null;
	        int cnt = 0;
	        try {
	            if(type ==1) {
	                cnt = replyService.likeCheck(likelist);
	            }else {
	                cnt = replyService.hateCheck(likelist);
	            }
//	            //System.out.println("cnt = " + cnt);
	            final BasicResponse result = new BasicResponse();
	            result.status = true;
	            result.data = "success";
	            if(cnt == 0) {
	                result.object = false;
	            } else {
	                result.object = true;
	            }
	            response = new ResponseEntity<>(result, HttpStatus.OK);            
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        
	        return response;
	        
	    }
	
}
