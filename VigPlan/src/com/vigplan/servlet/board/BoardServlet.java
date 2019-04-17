package com.vigplan.servlet.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vigplan.dao.board.BoardDao;
import com.vigplan.dao.member.MemberDao;
import com.vigplan.servlet.BaseServlet;
import com.vigplan.vo.BoardVo;
import com.vigplan.vo.MemberVo;

@WebServlet("/board")
public class BoardServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// Session 연동 -> 세션에서 사용자 확인
		HttpSession session = req.getSession();
		MemberVo authUser = (MemberVo) session.getAttribute("authUser");
		System.out.println("현재 사용자:" + authUser);

		if (authUser == null) {
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		String action = req.getParameter("a");

		if (action == null) {
			BoardDao dao = new BoardDao(dbuser, dbpass);

			List<BoardVo> list = dao.getAllLogs(); // 다 보여주는것
			req.setAttribute("list", list);
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/board_main.jsp");
			rd.forward(req, resp);

		} else if ("form".equals(action)) {
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/boardform.jsp");
			rd.forward(req, resp);
		} else if ("checkpw".equals(action)) {

			String id = req.getParameter("id");
			String password = req.getParameter("password");

			BoardVo vo = new BoardVo();
			vo.setId(Long.valueOf(id));
			vo.setPassword(password);
			System.out.println(vo);

			BoardDao dao = new BoardDao(dbuser, dbpass);

			vo = dao.getBoardItem(Long.valueOf(id), password);
			System.out.println(vo);

			if (vo != null) {
				resp.getWriter().print("success");
			} else {
				resp.getWriter().print("fail");
			}

		} else if ("show".equals(action)) {
			String id = req.getParameter("id");
			
			BoardDao dao = new BoardDao(dbuser, dbpass);
			BoardVo vo = dao.getBoardItem(Long.valueOf(id));// id의 값을 string으로 받아오니까
			
			Long memberNo = vo.getMemberNo();
			Long testMemberNo = authUser.getNo();
			
			if(memberNo.equals(testMemberNo)) {
				req.setAttribute("item", vo);

				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/board_show.jsp");
				rd.forward(req, resp);
				
			} else {
				req.setAttribute("item", vo);

				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/board_show2.jsp");
				rd.forward(req, resp);
				
			}
			
			
			
			

	

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("a");

		HttpSession session = req.getSession();
		MemberVo authUser = (MemberVo) session.getAttribute("authUser");

		// boardform 수행시 parameter
		if ("write".equals(action)) {
			String password = req.getParameter("password");
			String title = req.getParameter("title");
			String writer = req.getParameter("writer");
			String content = req.getParameter("content");
			String memberNo = req.getParameter("memberNo");
			// TODO: Session에서 authUser 받아와서 (null 체크) memberNo를 vo에 추가

			if (authUser == null) {
				resp.sendRedirect(req.getContextPath() + "/member/login");
				return;
			}
//			if(authUser != null) {
			BoardVo vo = new BoardVo();

			vo.setPassword(password);
			vo.setTitle(title);
			vo.setWriter(writer);
			vo.setContent(content);
			vo.setMemberNo(Long.valueOf(memberNo));
			System.out.println(vo);
			BoardDao dao = new BoardDao(dbuser, dbpass);
			/* int insertedCount = */dao.insertBoard(vo);

//			}

			// System.out.println("SUCCESS?:" + (insertedCount == 1));

			resp.sendRedirect(req.getServletContext().getContextPath() + "/board");

			// 메인창에서 title 클릭시 넘어가는 창(내용 보여주기)
		} else if ("edit".equals(action)) {

			String password = req.getParameter("password");
			System.out.println(password);
			String id = req.getParameter("id");

			BoardDao dao = new BoardDao(dbuser, dbpass);
			BoardVo vo = dao.getBoardItem(Long.valueOf(id));

			String password1 = dao.checkPw(Long.valueOf(id));
			System.out.println(password1);

			if (password.equals(password1)) {
				// id의 값을 string으로 받아오니까
				req.setAttribute("item", vo);
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/board_edit.jsp");
				// RequestDispatcher rd =
				// req.getRequestDispatcher("/WEB-INF/views/board/board_edit.jsp");
				rd.forward(req, resp);

			} else {
				vo.setPassword(password);
				req.setAttribute("item", vo);
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/board_pwcheck.jsp");
				rd.forward(req, resp);

			}

		} else if ("editer".equals(action)) {
			BoardDao dao = new BoardDao(dbuser, dbpass);

			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");

			BoardVo vo = dao.getBoardItem(Long.valueOf(id));

			if (title == null || title.length() == 0) {
				title = vo.getTitle();
			}
			if (content == null || content.length() == 0) {
				content = vo.getContent();
			}

			vo.setId(Long.valueOf(id));
			vo.setTitle(title);
			vo.setContent(content);

			System.out.println(vo.toString());
			// vo.setWriter(writer);
			// vo.setReg_date(reg_date);
			// vo.setview_cnt(view_cnt);

			int result = dao.updateBoard(vo);
			System.out.println("SUCCESS?:" + (result == 1));
			resp.sendRedirect(req.getServletContext().getContextPath() + "/board");

		} else if ("delete".equals(action)) {

			String id = req.getParameter("id");
			String password = req.getParameter("password");

			BoardVo vo = new BoardVo();
			vo.setPassword(password);
			vo.setId(Long.valueOf(id));

			if (password != null) {
				BoardDao dao = new BoardDao(dbuser, dbpass);
				int result = dao.deleteBoardItem(Long.valueOf(id));

				System.out.println("SUCCESS?:" + (result == 0));
				resp.sendRedirect(req.getServletContext().getContextPath() + "/board");

			}

		}

	}

}
