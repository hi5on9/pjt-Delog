package com.web.blog.controller.account.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.hashtag.UserHashtag;
import com.web.blog.jwt.JwtService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.ModifyInfo;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.user.UserService;
import com.web.blog.util.BCryptHelper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/user")
@RestController()
public class UserController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	public JavaMailSender javaMailSender;

	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptHelper bCrypt;

	@GetMapping("/account/userinfo")
	@ApiOperation(value = "JWT??? ?????? ??????")
	public Object userinfo(HttpServletRequest httpRequest) {
//		//System.out.println("/account/userinfo ??????]");
		User userOpt;

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		Map<String, Object> map = jwtService.get(httpRequest.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
		try {

			userOpt = userService.getUserByUid(uid);
//			//System.out.println("/account/userinfo ??????] userOpt : " + userOpt);

			if (userOpt != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = userOpt;
				ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
//				//System.out.println("/account/userinfo ??????] response : " + response);
				return response;
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/search")
	@ApiOperation(value = "????????????")
	public Object serachUserInfo(@RequestParam(required = true) final int uid) {
//		//System.out.println("/account/serach ??????] uid : " + uid);
		User userOpt;
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {

			userOpt = userService.getUserByUid(uid);
//			//System.out.println("/account/serach ??????] userOpt : " + userOpt);

			if (userOpt != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = userOpt;
				ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
//				//System.out.println("/account/serach ??????] response : " + response);
				return response;
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/login")
	@ApiOperation(value = "?????????")
	public Object login(@RequestParam(required = true) final String Email,
			@RequestParam(required = true) final String Password, HttpServletResponse res) {

//		//System.out.println("/account/login ??????] email : " + Email + ", password : " + Password);
		// ver1. jpa ??????
		// Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
		User userOpt;

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		
		try {
			userOpt = userService.login(new Login(0, Email, Password));
			//System.out.println("/user/login ??????] userOpt : " + userOpt);

			if (userOpt != null) {

				String token = jwtService.create(userOpt);
				// ?????? ????????? request??? ????????? ????????? ???????????? Map??? ????????????.
				res.setHeader("jwt_auth_token", token);
//		        res.setHeader("token", token);

				result.object = userOpt;
			} else {
				result.object = null;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		//System.out.println("/user/login ??????] response : " + response);
		return response;
	}

	@PostMapping("/account/signup")
	@ApiOperation(value = "????????????")
	public Object signup(@Valid @RequestBody SignupRequest request) {
		// public Object signup(@Valid @RequestBody User request) {
//		//System.out.println("user/account/signup ??????] request : " + request);
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		try {

			int cnt = userService.insertUser(new User(0, request.getEmail(), request.getPassword(),
					request.getNickname(), 0, request.getProvider(), request.getSnsid(), null, null));
//			//System.out.println("user/account/signup ??????] cnt : " + cnt);

			if (cnt == 1) {
				result.status = true;
				result.data = "200";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else if (cnt == -1) {
				result.status = false;
				result.data = "401";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else if (cnt == -2) {
				result.status = false;
				result.data = "400";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();

			result.status = false;
			result.data = "500";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		//System.out.println();
		return response;
	}

	@PutMapping("/account")
	@ApiOperation(value = "USER INFO ??????")
	public Object update(HttpServletRequest req, @Valid @RequestBody ModifyInfo info) {

		User userOpt;
		ResponseEntity response = null;

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		Map<String, Object> map = jwtService.get(req.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
//		//System.out.println("uid ??? ?????? ????????? = " + uid);

		try {
			User temp = userService.getUserByUid(uid);
			int cnt = -1;
			if (info.getType() == 1) {
				temp.setNickname(info.getNickname());
				cnt = userService.modifyNickNameUser(temp);
			} else {
				temp.setPassword(info.getPassword());
				cnt = userService.modifyPassWordUser(temp);
			}

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

	@DeleteMapping("/account")
	@ApiOperation(value = "User ??????")
	public Object delete(@RequestParam(required = true) final int uid) {
	
		ResponseEntity response = null;

		try {
			int cnt = userService.deleteUser(uid);
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

//	{"follower_id" : 1, "following_id": 2}
	@PostMapping("/follow")
	@ApiOperation(value = "??????  ?????????")
	public Object follow(@Valid @RequestBody Follow request) {
//		//System.out.println("/user/follow ??????] request : " + request);
		ResponseEntity response = null;
		try {
			int cnt = userService.follow(new Follow(request.getFollower_id(), request.getFollowing_id()));
//			//System.out.println("/user/follow ??????] cnt : " + cnt);
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
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

	@DeleteMapping("/unfollow")
	@ApiOperation(value = "?????? ????????? ??????")
	public Object unfollow(@RequestParam(required = true) final int f_uid,
			@RequestParam(required = true) final int s_uid) {
//		//System.out.println("/user/unfollow ??????] request : " + f_uid + " -> " + s_uid);
		ResponseEntity response = null;
		try {
			int cnt = userService.unFollow(new Follow(f_uid, s_uid));
//			//System.out.println("/user/unfollow ??????] cnt : " + cnt);
			if (cnt == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
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

	@GetMapping("/followlist")
	@ApiOperation(value = "????????? ?????????")
	public Object followlist(@RequestParam(required = true) final int uid,
			@RequestParam(required = true) final int type) {
		ResponseEntity response = null;

		List<FollowUser> list = null;
		// 1. ????????? 2. ?????????
		try {
			if (type == 1) {
				list = userService.getFollower(uid);
			} else {
				list = userService.getFollowing(uid);
			}

//			//System.out.println("GET) /follow ??????] (??????) list  : " + list);

			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (list != null) {
				result.object = list;
			} else {
				result.object = null;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	@GetMapping("/followcheck")
	@ApiOperation(value = "????????? ??????")
	public Object followcheck(@RequestParam(required = true) final int f_uid,
			@RequestParam(required = true) final int s_uid, @RequestParam(required = true) final int type) {

		ResponseEntity response = null;
		Follow follow;
		// type 1 : f_uid??? s_uid??? ????????? ??????
		// type 2 : s_uid??? f_uid??? ????????? ??????
		try {
			if (type == 1) {
				follow = userService.followerCheck(new Follow(f_uid, s_uid));
//				//System.out.println(
//						"GET) /follower check] : " + f_uid + " -> " + s_uid + " : " + (follow == null ? false : true));
			} else {
				follow = userService.followingCheck(new Follow(f_uid, s_uid));
//				//System.out.println(
//						"GET) /following check] : " + s_uid + " -> " + f_uid + " : " + (follow == null ? false : true));

			}

			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (follow != null) {
				result.object = true;
			} else {
				result.object = false;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	@PostMapping("/account/pwcheck")
	@ApiOperation(value = "PW ??????")
	public Object pwcheck(HttpServletRequest req, @Valid @RequestBody ModifyInfo info) {

		ResponseEntity response = null;

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		Map<String, Object> map = jwtService.get(req.getHeader("jwt_auth_token"));
		Map<String, Object> user = (Map<String, Object>) map.get("User");
		int uid = (int) user.get("uid");
		
		
		try {
			String pw = userService.getUserByUid(uid).getPassword();
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			System.out.println("db pw : " + pw + "?????? pw : " + info.getPassword());
			if (bCrypt.isMatch(info.getPassword(), pw)) {
				result.object = true;
			} else {
				result.object = false;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/account/signup/email/check")
	@ApiOperation(value = "???????????????")
	public Object emailCheck(@RequestParam(required = true) final String email) {

		ResponseEntity response = null;
		try {
			User user = userService.getUserByEmail(email);
			final BasicResponse result = new BasicResponse();
			if (user == null) {
				result.status = true;
				result.data = "success";
			} else {
				result.status = false;
				result.data = "faliure";
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/account/signup/nickname/check")
	@ApiOperation(value = "???????????????")
	public Object nicknamecheck(@RequestParam(required = true) final String nickname) {

		ResponseEntity response = null;

		try {
			User user = userService.getUserByNickname(nickname);
			final BasicResponse result = new BasicResponse();
			if (user == null) {
				result.status = true;
				result.data = "success";
			} else {
				result.status = false;
				result.data = "faliure";
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/login/kakao")
	@ApiOperation(value = "????????? ?????????")
	public Object kakaoLogin(@Valid @RequestBody SignupRequest request, HttpServletResponse res) {
		// public Object signup(@Valid @RequestBody User request) {
//		System.out.println("login/kakao ??????] request : " + request);
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		User userOpt;
		try {
			userOpt = userService.kakaoLoginUser(new User(0, request.getEmail(), request.getPassword(),
					request.getNickname(), 0, request.getProvider(), request.getSnsid(), null, request.getProfile()));
//			System.out.println("/user/login/kakao ??????] userOpt : " + userOpt);

			if (userOpt != null) {
				String token = jwtService.create(userOpt);
				// ?????? ????????? request??? ????????? ????????? ???????????? Map??? ????????????.
				res.setHeader("jwt_auth_token", token);
//		        res.setHeader("token", token);

				result.status = true;
				result.data = "success";
				result.object = userOpt;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		//System.out.println("/user/kakao/login??????] response : " + response);
		return response;
	}

	@GetMapping("/list")
	@ApiOperation(value = "?????? ?????????")
	public Object userlist() {
		ResponseEntity response = null;
		List<User> list = null;
		// 1. ????????? 2. ?????????
		try {

			list = userService.getUserList();

//			//System.out.println("GET) /userlist ??????] (??????) list  : " + list);

			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (list != null) {
				result.object = list;
			} else {
				result.object = null;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	@GetMapping("/account/email")
	@ApiOperation(value = "email ??????")
	public Object emailcheck(@RequestParam(required = true) final String email) {

		ResponseEntity response = null;

		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = sendMail(email);
		response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;

	}

	public String sendMail(String email) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("hd7107@naver.com"); // NAVER, DAUM, NATE??? ?????? ???????????? ???
		simpleMessage.setTo(email);
		simpleMessage.setSubject("DE-LOG ?????? ???????????????.");
		String captcha = makeCaptcha();
		simpleMessage.setText("???????????? : " + captcha);
		javaMailSender.send(simpleMessage);
		return captcha;
	}

	public String makeCaptcha() {
		String captcha = "";
		String pw = "";

		int random = 0;
		for (int i = 0; i < 20; i++) {
			random = (int) (Math.random() * 3);
			if (random == 0)
				captcha += (int) (Math.random() * 10);
			else if (random == 1)
				captcha += (char) (Math.random() * 26 + 97);
			else
				captcha += (char) (Math.random() * 26 + 65);
		}
		return captcha;
	}

	@Transactional
	@GetMapping("/hashtag/fields")
	@ApiOperation(value = "?????? ???????????? ??????")
	public Object getHashtagFields(@RequestParam(required = true) final int uid) {
		ResponseEntity response = null;
		UserHashtag userHashtag = null;
		System.out.println("/hashtag/fields : uid : " + uid);
		try {
			userHashtag = userService.getHashtagFields(uid);
			System.out.println("GET) /user/hashtag/fields ??????] (??????) userHashtag  : " + userHashtag);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (userHashtag != null) {
				result.object = userHashtag;
			} else {
				result.object = null;
			}
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	
}