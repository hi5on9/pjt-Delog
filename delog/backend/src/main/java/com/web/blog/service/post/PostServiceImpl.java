package com.web.blog.service.post;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.post.PostDao;
import com.web.blog.hashtag.HashtagDao;
import com.web.blog.model.DailyPostCount;
import com.web.blog.model.Hashtag;
import com.web.blog.model.Likes;
import com.web.blog.model.PostHasHashtag;
import com.web.blog.model.PostHashtag;
import com.web.blog.model.page.ListPage;
import com.web.blog.model.page.PostPage;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostDao postDao;
	@Autowired
	HashtagDao hashtagDao;

	@Override
	public PostUser getPost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - getPost] pid : " + pid);
		int cnt = postDao.hitPost(pid);
		//System.out.println("PostServiceImpl - getPost] 조회수 증가 : ");
		return postDao.getPost(pid);
	}

	@Override
	public List<PostUser> getPostList() throws SQLException {
		//System.out.println("PostServiceImpl - getPostList] ");
		return postDao.getPostList();
	}

	@Override
	@Transactional
	public int writePost(Post post) throws SQLException {
		//System.out.println("PostServiceImpl - writePost] post : " + post);
		if (post.getTitle() == null || post.getWriter() == null || post.getContents() == null)
			return -2;

		int cnt = postDao.writePost(post);

		//System.out.println("PostServiceImpl - writePost] cnt : " + cnt);
		// 해시 태그 각각 추가 (대소 문자 구분 X)
		// 해쉬 태그 POST HAS HASHTAG에 Pid, Hid 정보 추가
		// 1. HASHTAG 테이블에 title 존재하는지 확인.
		// 1-1] 존재 할 경우 count만 증가

		// 각각 요소 들에 대해 추가.
		if (post.getTags() != null) {
			List<String> tags = Arrays.asList(post.getTags().split(" "));
			//System.out.println("PostServiceImpl - writePost] tags : " + tags);

			for (String tag : tags) {
				//System.out.println("tag : " + tag);
				Hashtag hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());

				// 2. 존재하지 않다면 insert
				//System.out.println("존재 확인 hashtag : " + hashtag);

				if (hashtag == null) {
					hashtagDao.writeHashtag(tag);
					hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());
					//System.out.println("존재하지 않을 경우 생성 후 hashtag : " + hashtag);

				} else {
					hashtagDao.increaseHashtagCount(tag.toLowerCase());
					//System.out.println("존재할 경우 Count 증가 후 hashtag : " + hashtag);
				}

				// 3. Post has Hashtag table 확인
				PostHasHashtag postHashtag = hashtagDao
						.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
				//System.out.println("존재 확인 postHashtag : " + postHashtag);

				if (postHashtag == null) {
					hashtagDao.writePostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
					postHashtag = hashtagDao.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
					//System.out.println("존재하지 않을 경우 생성한 후 postHashtag : " + postHashtag);
				}
			} // end for tags;
		}
		
		//System.out.println("service 끝  = " + cnt);
		return cnt;

	}

	@Override
	public int modifyPost(Post post) throws SQLException {
		//System.out.println("PostServiceImpl - modifyPost] post : " + post);

		Post beforePost = postDao.getPost(post.getPid()).getPost();

		if (post.getTags() == beforePost.getTags()) {
			return postDao.modifyPost(post);
		} else {
			// 1. 기존에 있는 해쉬태그 를 가져와서 각각 다 지움
			String beforePostTags = beforePost.getTags();
			//System.out.println("beforePostTags : " + beforePostTags);

			if (beforePostTags != null) {
				List<String> beforeTags = Arrays.asList(beforePostTags.split(" "));
				//System.out.println("PostServiceImpl - modifyPost] tags : " + beforeTags);

				// 1-1. hashtag table 에서 title로 select 한 후 count가 1이면 지운다.
				for (String tag : beforeTags) {
					//System.out.println("tag : " + tag);
					Hashtag hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());
					if (hashtag != null) {
						if (hashtag.getCount() == 1) {
							hashtagDao.deleteHashtagByTitle(tag);
						} else {
							hashtagDao.decreaseHashtagCount(tag);

							// 1-2. Post has Hashtag table 확인
							PostHasHashtag postHashtag = hashtagDao
									.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
							//System.out.println("존재 확인 postHashtag : " + postHashtag);

							if (postHashtag != null) {
								hashtagDao.deletePostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
								//System.out.println("존재하지 않을 경우 생성한 후 postHashtag : " + postHashtag);
							}
						}
					}
				} // end for tags;

			}

			// 수정 된 해쉬태그 추가
			// 각각 요소 들에 대해 추가.
			if (beforePostTags != null) {
				List<String> tags = Arrays.asList(post.getTags().split(" "));
				//System.out.println("PostServiceImpl - modifyPost] tags : " + tags);

				for (String tag : tags) {
					//System.out.println("tag : " + tag);
					Hashtag hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());

					// 2. 존재하지 않다면 insert
					//System.out.println("존재 확인 hashtag : " + hashtag);

					if (hashtag == null) {
						hashtagDao.writeHashtag(tag);
						hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());
						//System.out.println("존재하지 않을 경우 생성 후 hashtag : " + hashtag);

					} else {
						hashtagDao.increaseHashtagCount(tag.toLowerCase());
						//System.out.println("존재할 경우 Count 증가 후 hashtag : " + hashtag);
					}

					// 3. Post has Hashtag table 확인
					PostHasHashtag postHashtag = hashtagDao
							.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
					//System.out.println("존재 확인 postHashtag : " + postHashtag);

					if (postHashtag == null) {
						hashtagDao.writePostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
						postHashtag = hashtagDao
								.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
						//System.out.println("존재하지 않을 경우 생성한 후 postHashtag : " + postHashtag);
					}
				} // end for tags;
			}

		}

		return postDao.modifyPost(post);
	}

	@Override
	public int deletePost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - deletePost] pid : " + pid);

		Post post = postDao.getPost(pid).getPost();
		// 해시태그 처리

		String postTags = post.getTags();
		if (postTags != null) {
			List<String> tags = Arrays.asList(postTags.split(" "));
			//System.out.println("PostServiceImpl - deletePost] tags : " + tags);

			// hashtag table 에서 title로 select 한 후 count가 1이면 지운다.
			for (String tag : tags) {
				//System.out.println("tag : " + tag);
				Hashtag hashtag = hashtagDao.selectHashTagByTitle(tag.toLowerCase());
				if (hashtag != null) {
					if (hashtag.getCount() == 1) {
						hashtagDao.deleteHashtagByTitle(tag);
					} else {
						hashtagDao.decreaseHashtagCount(tag);

						// 1-2. Post has Hashtag table 확인
						PostHasHashtag postHashtag = hashtagDao
								.selectPostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
						//System.out.println("존재 확인 postHashtag : " + postHashtag);

						if (postHashtag != null) {
							hashtagDao.deletePostHasHashTag(new PostHasHashtag(post.getPid(), hashtag.getHid()));
							//System.out.println("존재하지 않을 경우 생성한 후 postHashtag : " + postHashtag);
						}
					}
				}
			} // end for tags;
		}

		return postDao.deletePost(pid);
	}

	@Override
	public List<PostUser> getMyPostList(ListPage page) throws SQLException {
		//System.out.println("PostServiceImpl - getMyPostList] page : " + page);
		return postDao.getMyPostList(page);
	}

	@Override
	public List<PostUser> getListByTitle(PostPage page) throws SQLException {
		//System.out.println("PostServiceImpl - getListByTitle] page : " + page);
		return postDao.getListByTitle(page);
	}

	@Override
	public List<PostUser> getListByContents(PostPage page) throws SQLException {
		//System.out.println("PostServiceImpl - getListByContents] contents : " + page);
		return postDao.getListByContents(page);
	}

	@Override
	public List<PostUser> getListByNickname(PostPage page) throws SQLException {
		//System.out.println("PostServiceImpl - getListByNickname] nickname : " + page);
		return postDao.getListByNickname(page);
	}

	@Override
	public List<PostHashtag> getListByHashtag(PostPage page) throws SQLException {
//		System.out.println("PostServiceImpl - getListByNickname] hashtag : " + page);
//		System.out.println(postDao.getListByHashtag(page));
		return postDao.getListByHashtag(page);
	}

	@Override
	public int hitPost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - hitPost] pid : " + pid);
		return postDao.hitPost(pid);
	}

	@Override
	public int likePost(Likes likes) throws SQLException {
		//System.out.println("PostServiceImpl - likePost] likes : " + likes);
		return postDao.likePost(likes);
	}

	@Override
	public int unlikePost(Likes likes) throws SQLException {
		//System.out.println("PostServiceImpl - unLikePost] likes : " + likes);
		return postDao.unlikePost(likes);
	}

	@Override
	public Likes likeCheck(Likes like) throws SQLException {
		//System.out.println("PostServiceImpl - likeCheck] like : " + like);
		if (like.getPid() == 0 || like.getUid() == 0)
			return null;
		return postDao.likeCheck(like);
	}

	@Override
	public List<PostUser> getLikeList(ListPage page) throws SQLException {
		System.out.println("PostServiceImpl - getLikeList] page : " + page);
		return postDao.getLikeList(page);
	}

	@Override
	public List<PostUser> getRecentList() throws SQLException {
		//System.out.println("PostServiceImpl - getRecentList] ");
		return postDao.getRecentList();
	}

	@Override
	public List<PostUser> getTempList(int uid) throws SQLException {
		//System.out.println("PostServiceImpl - getTempList] ");
		return postDao.getTempList(uid);
	}

	@Override
	public int writeTempPost(Post post) throws SQLException {
		//System.out.println("PostServiceImpl - writeTempPost] ");
		return postDao.writeTempPost(post);
	}

	@Override
	public int modifyTempPost(Post post) throws SQLException {
		//System.out.println("PostServiceImpl - modifyTempPost] ");
		return postDao.modifyTempPost(post);
	}

	@Override
	public int deleteTempPost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - deleteTempPost] ");
		return postDao.deleteTempPost(pid);
	}

	@Override
	public int changeTempPost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - changeTempPost] ");
		return postDao.changeTempPost(pid);
	}

	@Override
	public List<PostUser> getHideList(int uid) throws SQLException {
		//System.out.println("PostServiceImpl - getHideList] ");
		return postDao.getHideList(uid);
	}

	@Override
	public int hidePost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - hidePost] ");
		return postDao.hidePost(pid);
	}

	@Override
	public int unhidePost(int pid) throws SQLException {
		//System.out.println("PostServiceImpl - unhidePost] ");
		return postDao.unhidePost(pid);
	}

	@Override
	public int getTitleCount(String title) throws SQLException {
		return postDao.getTitleCount(title);
	}

	@Override
	public int getContentsCount(String contents) throws SQLException {
		return postDao.getContentsCount(contents);
	}

	@Override
	public int getNicknameCount(String nickname) throws SQLException {
		return postDao.getNicknameCount(nickname);
	}

	@Override
	public int getHashtagCount(String hashtag) throws SQLException {
		return postDao.getHashtagCount(hashtag);
	}

	@Override
	public List<DailyPostCount> getDailyPostCount(int uid) throws SQLException {
		return postDao.getDailyPostCount(uid);
	}
	
	
	

}
