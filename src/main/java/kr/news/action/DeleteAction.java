package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		String passwd = request.getParameter("passwd");
		
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO db_news = dao.getNews(num);
		boolean check = false;
		if(db_news!=null) {
			check =db_news.isCheckedPassword(passwd);
		}
		if(check) {
			dao.deleteNews(num);
			//파일 삭제
			FileUtil.removeFile(request, db_news.getFilename());
		}
		//UI 처리 위해서 check 저장
		request.setAttribute("check", check);
		return "/WEB-INF/views/delete.jsp";
	}

}
