package com.web.blog.controller.account.post;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.file.FileService;
import com.web.blog.file.Files;
import com.web.blog.file.PostFiles;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.DailyPostCount;
import com.web.blog.model.Likes;
import com.web.blog.model.page.ListPage;
import com.web.blog.model.page.PostPage;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;
import com.web.blog.service.post.PostService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RequestMapping("/post")
@RestController()
public class PostController {

	@Value("${file.upload.directory}")
	String uploadFileDir;

	@Value("{file.upload.path}")
	String uploadFilePath;

	@Autowired
	PostService postService;

	@Autowired
	FileService fileService;

	@PostMapping("")
	@ApiOperation(value = "Post 등록")
//	, @RequestPart MultipartFile files
	public Object post(@Valid @RequestBody Post request) {
		ResponseEntity response = null;
//		//System.out.println("POST) /post 요청] (Post 등록) request : " + request);
		try {
//			List tags = request.getTags();
//			String tags = String.join(" ", request.getTags());
//			//System.out.println("/post 요청] (Post 등록) tags : " + tags);

			if (request.getTags().equals(""))
				request.setTags(null);

			Post insertPostInfo = new Post(0, request.getWriter(), request.getTitle(), request.getContents(), 0, false,
					null, request.getTags(), 0);

//			//System.out.println("writePost 시작 ");
			int cnt = postService.writePost(insertPostInfo);
//			//System.out.println("insertPostInfo : " + insertPostInfo);

			String a = "123";
//			//System.out.println("if문 시작");
			if (cnt == 1) {
				int pid = insertPostInfo.getPid();
				// 파일이 있으면
//				if (!files.isEmpty()) {
//					//System.out.println("123123");
//					String fileName = files.getOriginalFilename();
//					String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
//
//					String uploadFileDirTemp = uploadFileDir + pid + "/";
//
//					File destinationFile;
//					String destinationFileName;
//					//
//					do {
//						destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
//						destinationFile = new File(uploadFileDirTemp + destinationFileName);
//					} while (destinationFile.exists());
//
//					destinationFile.getParentFile().mkdirs();
//					try {
//						InputStream fileStream = files.getInputStream();
//						FileUtils.copyInputStreamToFile(fileStream, destinationFile);
//						String filePath = "/static/upload/files/" + pid + "/" + destinationFileName;
//						Files fileInfo = new Files(0, pid, destinationFileName, fileName, filePath, null);
//
//						try {
//							cnt = fileService.insertFile(new PostFiles(pid, fileInfo));
//							//System.out.println("/post fileService.insertFile] cnt : " + cnt);
//						} catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				} // end if
				
//				//System.out.println("kkkkkk");
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
//				//System.out.println("response : " + response);
			} else {
				
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
//				//System.out.println("response : " + response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("")
	@ApiOperation(value = "Post 수정")
	public Object update(@Valid @RequestBody Post request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /post 요청] (Post 수정) request : " + request);
		try {
			int cnt = postService.modifyPost(new Post(request.getPid(), request.getWriter(), request.getTitle(),
					request.getContents(), 0, false, null, request.getTags(), 0));
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
	@ApiOperation(value = "POST 삭제")
	public Object delete(@RequestParam(required = true) final int pid) {
		ResponseEntity response = null;

//		//System.out.println("DELETE) /post 요청] (Post 삭제) pid : " + pid);

		try {
			int cnt = postService.deletePost(pid);
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
	@ApiOperation(value = "상세페이지")
	public Object detail(@RequestParam(required = true) final int pid) {
//		//System.out.println("GET) /post/detail 요청] (상세페이지) pid : " + pid);

		ResponseEntity response = null;
		PostUser selectPost = null;
		try {
			selectPost = postService.getPost(pid);	
//			//System.out.println("GET) /post/detail 요청] selectPost : " + selectPost);

			if (selectPost != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = selectPost;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "failure";
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return response;
	}

	@GetMapping()
	@ApiOperation(value = "전체 조회")
	public Object allsearch() {
//		//System.out.println("GET) /post 요청] (전체조회 )");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = postService.getPostList();

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

	@GetMapping("/mypost")
	@ApiOperation(value = "내 모든 post")
	public Object mypost(@RequestParam(required = true) final int uid, @RequestParam(required = true) final int page) {
//		//System.out.println("GET) /post 요청] (내 모든 post)");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;
		ListPage paging = new ListPage(uid, page * 6);

		try {
			list = postService.getMyPostList(paging);
			//
//			//System.out.println("GET) /post 요청] (내 모든 post) list : " + list);
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

	@GetMapping("/search")
	@ApiOperation(value = " 검색")
	public Object titlesearch(@RequestParam(required = true) final int type,
			@RequestParam(required = true) final String value, @RequestParam(required = true) final int page) {

//		//System.out.println("GET) /search 요청] (검색) type : " + type + ", value : " + value);

		List list = new ArrayList<>();
		ResponseEntity response = null;
		PostPage paging = new PostPage(value, page * 6, 0, null);
		int count = 0;
		// 1 : hashtag 2 : title 3 : contents 4 : nickname
		//System.out.println(value);
		try {
			switch (type) {
			case 1:
				if(value == "") {
					//해쉬태그 없으면 전체랑 똑같음(contents로 하는거나 같음)
					count = postService.getContentsCount(value);
//					System.out.println("count : " + count);
					list = postService.getListByContents(paging);
//					System.out.println("GET) value==/search 요청] (검색) list  : " + list);
				}else {
					System.out.println("else");
					count = postService.getHashtagCount(value);
//					System.out.println("count : " + count);
					list = postService.getListByHashtag(paging);
//					System.out.println("GET) value!=/search 요청] (검색) list  : " + list);
				}
				break;
			case 2:
				list = postService.getListByTitle(paging);
				count = postService.getTitleCount(value);
				//System.out.println(count);
				break;
			case 3:
				list = postService.getListByContents(paging);
				count = postService.getContentsCount(value);
				break;
			case 4:
				list = postService.getListByNickname(paging);
				count = postService.getNicknameCount(value);
				break;

			}
//			System.out.println("GET) /search 요청] (검색) list  : " + list);

			if (list != null) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = String.valueOf(count);
				result.object = list;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = String.valueOf(count);
				result.object = list;
				response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	@GetMapping("/like")
	@ApiOperation(value = "좋아요 확인")
	public Object likecheck(@RequestParam(required = true) final int pid,
			@RequestParam(required = true) final int uid) {

		ResponseEntity response = null;
		Likes like = null;

		try {
			like = postService.likeCheck(new Likes(uid, pid));
//			//System.out.println("GET) /likeCheck] : " + uid + " -> " + pid + " : " + like);

			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			if (like != null) {
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

	@PostMapping("/like")
	@ApiOperation(value = "게시물 좋아요")
	public Object like(@Valid @RequestBody Likes request) {
//		//System.out.println("/post/like 요청] request : " + request);
		ResponseEntity response = null;
		try {
			int cnt = postService.likePost(new Likes(request.getUid(), request.getPid()));
//			//System.out.println("/post/like 요청] cnt : " + cnt);
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

	@DeleteMapping("/unlike")
	@ApiOperation(value = "게시물 좋아요 취소")
	public Object unlike(@RequestParam(required = true) final int pid, @RequestParam(required = true) final int uid) {
//		//System.out.println("/post/unlike 요청] request : " + pid + " -> " + uid);
		ResponseEntity response = null;
		try {
			int cnt = postService.unlikePost(new Likes(uid, pid));
			//System.out.println("/post/unlike 요청] cnt : " + cnt);
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

	// 임시 저장 부분

	@GetMapping("/templist")
	@ApiOperation(value = "임시저장 리스트")

	public Object templist(@RequestParam(required = true) final int uid) {

//		//System.out.println("GET) /post 요청] (임시저장 리스트)");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = postService.getTempList(uid);
			//
//			//System.out.println("GET) /post 요청] (리스트 ) list : " + list);
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

	@PostMapping("/temp")
	@ApiOperation(value = "Temp 등록")
	public Object posttemp(@Valid @RequestBody Post request) {
		ResponseEntity response = null;

//		//System.out.println("POST) /post 요청] (Temp 등록) request : " + request);

		try {
			int cnt = postService.writeTempPost(new Post(0, request.getWriter(), request.getTitle(),
					request.getContents(), 0, false, null, request.getTags(), 0));
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

	@PutMapping("/temp")
	@ApiOperation(value = "Temp 수정")
	public Object updatetemp(@Valid @RequestBody Post request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /post 요청] (Temp 수정) request : " + request);

		try {
			int cnt = postService.modifyTempPost(new Post(request.getPid(), request.getWriter(), request.getTitle(),
					request.getContents(), 0, true, null, request.getTags(), 0));
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

	@DeleteMapping("/temp")
	@ApiOperation(value = "Temp 삭제")
	public Object deletetemp(@RequestParam(required = true) final int pid) {
		ResponseEntity response = null;

//		//System.out.println("DELETE) /post 요청] (Temp 삭제) pid : " + pid);

		try {
			int cnt = postService.deleteTempPost(pid);
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

	@PutMapping("/tempchange")
	@ApiOperation(value = "Temp -> Post 등록")
	public Object updatetempchange(@Valid @RequestBody Map<String, Integer> request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /post 요청] (Temp -> Post 등록) pid : " + request.get("pid"));
//		//System.out.println(request.get("pid"));
		try {
			int cnt = postService.changeTempPost(request.get("pid"));
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

	// 게시글 숨기기
	@GetMapping("/hidelist")
	@ApiOperation(value = "숨김 리스트")
	public Object hidelist(@RequestParam(required = true) final int uid) {
//		//System.out.println("GET) /post 요청] (숨김 리스트)");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = postService.getHideList(uid);
			//
//			//System.out.println("GET) /post 요청] (숨김 리스트 ) list : " + list);
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

	@PutMapping("/hidden")
	@ApiOperation(value = "숨김 변경")
	public Object updatetemp(@Valid @RequestBody Map<String, Integer> request) {
		ResponseEntity response = null;

//		//System.out.println("PUT) /post 요청] (숨김 변경) pod : " + pid + " - type :  " + (type == 1 ? "hide" : "unhide"));
//		//System.out.println(request.get("pid") + " " + request.get("type"));
		int cnt = 0;
		try {
			if (request.get("type") == 1) {
				cnt = postService.hidePost(request.get("pid"));
			} else {
				cnt = postService.unhidePost(request.get("pid"));
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

	@GetMapping("/likelist")
	@ApiOperation(value = "좋아요 글 리스트")
	public Object likelist(@RequestParam(required = true) final int uid,
			@RequestParam(required = true) final int page) {
		ResponseEntity response = null;
		List<PostUser> list = null;
		// 1. 팔로워 2. 팔로잉
		ListPage paging = new ListPage(uid, page);
		System.out.println("paging : " + paging );

		try {
			list = postService.getLikeList(paging);
			System.out.println("GET) /like list 요청] (검색) list  : " + list);
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

	@GetMapping("/recentlist")
	@ApiOperation(value = "최근 등록 top5 글")
	public Object recentlist() {
//		//System.out.println("GET) /post 요청] (최근 등록 top5 글)");
		List<PostUser> list = new ArrayList<>();
		ResponseEntity response = null;

		try {
			list = postService.getRecentList();
			//
//			//System.out.println("GET) /post 요청] (최근 등록 top5 글 ) list : " + list);
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
	
	@GetMapping("/daily/count")
	@ApiOperation(value = "Post daily count")
	public Object dailyCount(@RequestParam(required = true) final int uid) {
		ResponseEntity response = null;

//		System.out.println("DELETE) /post 요청] (Post 삭제) pid : " + pid);

		try {
			List<DailyPostCount> list = postService.getDailyPostCount(uid);
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