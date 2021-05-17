package com.web.blog.dao.post;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.DailyPostCount;
import com.web.blog.model.Hashtag;
import com.web.blog.model.Likes;
import com.web.blog.model.PostHashtag;
import com.web.blog.model.page.ListPage;
import com.web.blog.model.page.PostPage;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<PostUser> getPostList() throws SQLException {
		//System.out.println("PostDaoImpl - getPostList] ");
		List<PostUser> list = new ArrayList<PostUser>();
		list = sqlSession.selectList("POST.selectAllPost");
		return list;
	}

	@Override
	public PostUser getPost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - getPost] pid : " + pid);
		PostUser vo = sqlSession.selectOne("POST.selectPost", pid);
		//System.out.println("PostDaoImpl - getPost] vo : " + pid);
		return vo;
	}

	@Override
	public int writePost(Post post) throws SQLException {
		//System.out.println("PostDaoImpl - writePost] post : " + post);
		int cnt = sqlSession.insert("POST.createPost", post);
		//System.out.println("PostDaoImpl - writePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int modifyPost(Post post) throws SQLException {
		//System.out.println("PostDaoImpl - modifyPost] post : " + post);
		int cnt = sqlSession.update("POST.updatePost", post);
		//System.out.println("PostDaoImpl - modifyPost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int deletePost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - deletePost] pid : " + pid);
		int cnt = sqlSession.delete("POST.deletePost", pid);
		//System.out.println("PostDaoImpl - deletePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public List<PostUser> getMyPostList(ListPage page) throws SQLException {
		//System.out.println("PostDaoImpl - getMyPostList] page : " + page);
		List<PostUser> list = sqlSession.selectList("POST.selectMyPost", page);
		//System.out.println("PostDaoImpl - getMyPostList] list : " + list);
		return list;
	}

	@Override
	public List<PostHashtag> getListByHashtag(PostPage page) throws SQLException {
//		# 1. title로 hashtag table의 pid를 찾아낸다.
		List<PostHashtag> list = new ArrayList<>();

		//System.out.println("title : "  +page.getTitle());
		
		/*
		if (page.getTitle() != "") {
			//System.out.println("HashtagDaoImpl - getListByHashtag]  page : " + page);
			Hashtag hashtag = sqlSession.selectOne("HASHTAG.selectHashTagByTitle", page.getTitle());
			//System.out.println("HashtagDaoImpl - getListByHashtag]  hashtag : " + hashtag);

			if (hashtag != null) {
//		# 2. Hid가 존재하는 Pid를 포함하는 Post목록들의 내용을 다 보여줌.
				int hid = hashtag.getHid();
				//System.out.println("HashtagDaoImpl - getListByHashtag]  hid : " + hid);
				//System.out.println("PostDaoImpl - getListByHashtag] page : " + page);

				page.setHid(hid);
				//System.out.println("PostDaoImpl - getListByHashtag] page : " + page);
				list = sqlSession.selectList("POST.selectPostByHashTag", page);
				//System.out.println("PostDaoImpl - getListByHashtag]  : " + list.size() + "개 검색, list : " + list);
//		//System.out.println(list.size() != 0 ? "hashtag List success" : "hashtag list fail");
			}
		} else {
			//System.out.println("PostDaoImpl - getListByHashtag] page : " + page);
			list = sqlSession.selectList("POST.selectNoHiddenAllPosts", page);
			//System.out.println("PostDaoImpl - getListByHashtag]  : " + list.size() + "개 검색, list : " + list);
		}
		*/
		
		list = sqlSession.selectList("POST.selectPostByHashTag", page);
//		System.out.println("PostDaoImpl - getListByHashtag]  : " + list.size() + "개 검색, list : " + list);
		return list;
	}

	@Override
	public List<PostUser> getListByTitle(PostPage page) throws SQLException {
		//System.out.println("PostDaoImpl - getListByTitle] page : " + page);
		List<PostUser> list = new ArrayList<>();
		list = sqlSession.selectList("POST.selectPostsByTitle", page);
		//System.out.println("PostDaoImpl - getListByTitle]  : " + list.size() + "개 검색, list : " + list);
		return list;
	}

	@Override
	public List<PostUser> getListByContents(PostPage page) throws SQLException {
		//System.out.println("PostDaoImpl - getListByContents] page : " + page);
		List<PostUser> list = new ArrayList<>();
		list = sqlSession.selectList("POST.selectPostsByContents", page);
		//System.out.println("PostDaoImpl - getListByContents]  : " + list.size() + "개 검색, list : " + list);
		return list;
	}

	@Override
	public List<PostUser> getListByNickname(PostPage page) throws SQLException {
		//System.out.println("PostDaoImpl - getListByNickname] page : " + page);
		List<PostUser> list = new ArrayList<>();
		list = sqlSession.selectList("POST.selectPostsByNickname", page);
		//System.out.println("PostDaoImpl - getListByNickname]  : " + list.size() + "개 검색 , list : " + list);
		return list;
	}

	@Override
	public int hitPost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - hitPost] pid : " + pid);
		int cnt = sqlSession.update("POST.updateHit", pid);
		//System.out.println("PostDaoImpl - hitPost]");
		return cnt;
	}

	@Override
	public int likePost(Likes likes) throws SQLException {
		//System.out.println("PostDaoImpl - likePost] likes : " + likes);
		int cnt = sqlSession.insert("LIKES.likePost", likes);
		//System.out.println("PostDaoImpl - likePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int unlikePost(Likes likes) throws SQLException {
		//System.out.println("PostDaoImpl - unlikePost] likes : " + likes);
		int cnt = sqlSession.delete("LIKES.unLikePost", likes);
		//System.out.println("PostDaoImpl - unlikePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public Likes likeCheck(Likes like) throws SQLException {
		//System.out.println("PostDaoImpl - likeCheck] like : " + like);
		Likes likes = sqlSession.selectOne("LIKES.likeCheck", like);
		//System.out.println("PostDaoImpl - likeCheck] flag : " + likes);
		return likes;
	}

	@Override
	public List<PostUser> getLikeList(ListPage page) throws SQLException {
		System.out.println("PostDaoImpl - getLikeList] uid : " + page.getUid());
		List<PostUser> list = new ArrayList<PostUser>();
		list = sqlSession.selectList("LIKES.likeList", page);
		System.out.println("PostDaoImpl - getLikeList]  : " + list.size() + "개 검색 , list : " + list);
		return list;
	}

	@Override
	public List<PostUser> getRecentList() throws SQLException {
		//System.out.println("PostDaoImpl - getRecentList] ");
		List<PostUser> list = new ArrayList<PostUser>();
		list = sqlSession.selectList("POST.selectRecentPost");
		//System.out.println("PostDaoImpl - getRecentList] list : " + list);
		return list;
	}

	@Override
	public List<PostUser> getTempList(int uid) throws SQLException {
		//System.out.println("PostDaoImpl - getTempList] ");
		List<PostUser> list = new ArrayList<PostUser>();
		list = sqlSession.selectList("POST.TempList", uid);
		//System.out.println("PostDaoImpl - getTempList] list : " + list);
		return list;
	}

	@Override
	public int writeTempPost(Post post) throws SQLException {
		//System.out.println("PostDaoImpl - writeTempPost] post : " + post);
		int cnt = sqlSession.insert("POST.createTemp", post);
		//System.out.println("PostDaoImpl - writeTempPost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int modifyTempPost(Post post) throws SQLException {
		//System.out.println("PostDaoImpl - modifyTempPost] post : " + post);
		int cnt = sqlSession.update("POST.updateTemp", post);
		//System.out.println("PostDaoImpl - modifyTempPost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int deleteTempPost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - deleteTempPost] pid : " + pid);
		int cnt = sqlSession.delete("POST.deleteTemp", pid);
		//System.out.println("PostDaoImpl - deleteTempPost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int changeTempPost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - changeTempPost] post : " + pid);
		int cnt = sqlSession.update("POST.changeTemp", pid);
		//System.out.println("PostDaoImpl - changeTempPost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public List<PostUser> getHideList(int uid) throws SQLException {
		//System.out.println("PostDaoImpl - getHideList] ");
		List<PostUser> list = new ArrayList<PostUser>();
		list = sqlSession.selectList("POST.hiddenList", uid);
		//System.out.println("PostDaoImpl - getHideList] list : " + list);
		return list;
	}

	@Override
	public int hidePost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - hidePost] int pid : " + pid);
		int cnt = sqlSession.update("POST.hidePost", pid);
		//System.out.println("PostDaoImpl - hidePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int unhidePost(int pid) throws SQLException {
		//System.out.println("PostDaoImpl - unhidePost] int pid : " + pid);
		int cnt = sqlSession.update("POST.unhidePost", pid);
		//System.out.println("PostDaoImpl - unhidePost] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int getTitleCount(String title) throws SQLException {
		int cnt = sqlSession.selectOne("POST.selectPostByTitleCount", title);
		return cnt;
	}

	@Override
	public int getContentsCount(String contents) throws SQLException {
		int cnt = sqlSession.selectOne("POST.selectPostByContentsCount", contents);
		return cnt;
	}

	@Override
	public int getNicknameCount(String nickname) throws SQLException {
		int cnt = sqlSession.selectOne("POST.selectPostByNicknameCount", nickname);
		return cnt;
	}

	@Override
	public int getHashtagCount(String hashtag) throws SQLException {
		int cnt = sqlSession.selectOne("POST.selectPostByHashTagCount", hashtag);
//		sysout
//		int hid = 0, cnt = 0;
//		if (hashtag != "") {
//			Hashtag tags = sqlSession.selectOne("HASHTAG.selectHashTagByTitle", hashtag);
//
//			if (hashtag != null) {
//				hid = tags.getHid();
//				//System.out.println("hid -> " + hid);
//
//				cnt = sqlSession.selectOne("POST.selectPostByHashTagCount", hid);
//			}
//		} else {
//			cnt = sqlSession.selectOne("POST.selectPostByHashTagCount", hid);
//		}
//		
		return cnt;
	}

	@Override
	public List<DailyPostCount> getDailyPostCount(int uid) throws SQLException {
		return sqlSession.selectList("POST.selectDailyCount", uid);
	}

	
}
