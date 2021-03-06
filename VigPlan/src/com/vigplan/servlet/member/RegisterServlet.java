package com.vigplan.servlet.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vigplan.dao.member.MemberDao;
import com.vigplan.servlet.BaseServlet;
import com.vigplan.vo.MemberVo;

@WebServlet("/member")
public class RegisterServlet extends BaseServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = request.getParameter("a");
		
		if (action == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/member/register_form.jsp");
			rd.forward(request, response);
		} else if ("success".equals(action)) {
			//	가입 성공 VIEW JSP로 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/member/login_success.jsp");
			rd.forward(request, response);
			
		}
	}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
				throws ServletException, IOException {
			
			req.setCharacterEncoding("UTF-8");
			
			String id = req.getParameter("id");
			String password = req.getParameter("pw");
			String nickname = req.getParameter("nickname");
			String email = req.getParameter("email");
			
			resp.setContentType("text/html;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			
			MemberVo vo = new MemberVo();
			vo.setId(id);
			vo.setPw(password);
			vo.setNickname(nickname);
			vo.setEmail(email);
			
			MemberDao dao = new MemberDao(dbuser, dbpass);
			int insertedCount = dao.insertMember(vo);
			
			System.out.println("SUCCESS?:" + (insertedCount == 1));
			
			resp.sendRedirect(req.getContextPath() + "/member?a=success");

		} 
		
	}





