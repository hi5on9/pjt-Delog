package com.web.blog.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.blog.jwt.JwtService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.UserAndCount;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.FollowPost;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/main")
@Controller
public class MainController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	MainService mainService;

	@GetMapping("/auth/list/recent")
	@ApiOperation(value = "팔로잉한 사람 공개 게시물 최신순으로 sort")
	public Object getFollowingList(HttpServletRequest httpRequest) {
		//System.out.println("/main/auth/list/recent 요청]");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		Map<String, Object> map = jwtService.get(httpRequest.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");

		List<FollowPost> followPost = new ArrayList<>();
		ResponseEntity response = null;

		int uid = (int) user.get("uid");
		
		try {
			followPost = mainService.getfollowingsRecentPost(uid);
			//System.out.println("/main/auth/list/recent 요청] followPost : " + followPost);

			if (followPost != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = followPost;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/list/order/follower")
	@ApiOperation(value = "팔로워가 많은 유저")
	public Object getUidsByMostfollowerCnt() {
		//System.out.println("GET) main/list/order/follower 요청]");
		List<UserAndCount> list = new ArrayList<>();
		ResponseEntity response = null;
		try {
			list = mainService.getFolloweringUidsOrderByFollowerCount();
			//System.out.println("GET) main/list/order/follower 요청] list : " + list);
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
			e.printStackTrace();
		}
		return response;
	}
	
	
	@GetMapping("/list/order/postcnt")
	@ApiOperation(value = "게시글을 많이 쓴 작가")
	public Object getUidsByManyPosts() {
		//System.out.println("GET) /main/list/order/postcnt 요청]");
		List<UserAndCount> list = new ArrayList<>();
		ResponseEntity response = null;
		try {
			list = mainService.getUidsOrderByManyPostCount();
			//System.out.println("GET) /main/list/order/postcnt list : " + list);
			// if (userOpt.isPresent()) {
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
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/list/order/hit")
	@ApiOperation(value = "Hit가 높은 게시물")
	public Object getHighHitPosts() {
		//System.out.println("GET) /main/list/order/hit 요청]");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;
		try {
			list = mainService.getPostsOrdeyByManyHit();
			//System.out.println("GET) /main/list/order/hit : " + list);
			// if (userOpt.isPresent()) {
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
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/list/order/recent")
	@ApiOperation(value = "최근 게시물")
	public Object getRecentPosts() {
		//System.out.println("GET) /main/list/order/recent 요청]");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;
		try {
			list = mainService.getRecentPosts();
			//System.out.println("GET) /main/list/order/recent : " + list);
			// if (userOpt.isPresent()) {
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
			e.printStackTrace();
		}
		return response;
	}
	
	
}
