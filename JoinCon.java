package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.MemberDAO;
import Model.MemberDTO;

@WebServlet("/JoinCon")
public class JoinCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		String phone = request.getParameter("phone");
		String[] birthData = request.getParameterValues("birth");
		String birth = birthData[0] + "-" + birthData[1] + "-" + birthData[2];
		int sex = Integer.parseInt(request.getParameter("sex"));
		
		MemberDAO dao = new MemberDAO();
		int cnt = dao.memberJoin(new MemberDTO(id,pw,nick,phone,birth,sex));
		
		if (cnt>0 ) {
			// ȸ������ �Ϸ�! ������������ �̵��մϴ� ��� �� �̵�
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('ȸ������ ����!');");
			out.print("location.href='main.jsp';");
			out.print("</script>");
		} else {
			// ȸ������ ����!
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('ȸ������ ����');");
			// ������������ �̵��մϴ�
			out.print("location.href='main.jsp';");
			out.print("</script>");
		}
		
		
	}

}