package com.xihui.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.iap.Response;
import com.xurui.dormain.TestSet;
import com.xurui.util.TestSetUtil;

public class UpdateTestServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateTestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String course = new String(request.getParameter("course").getBytes("iso8859-1"),"gbk");
		int singleCount =Integer.parseInt(request.getParameter("singleCount"));
		int perSingle = Integer.parseInt(request.getParameter("perSingle"));
		int multiplyCount=Integer.parseInt(request.getParameter("multiplyCount"));
		int perMultiply = Integer.parseInt(request.getParameter("perMultiply"));
		int judgeCount = Integer.parseInt(request.getParameter("judgeCount"));
		int perJudge = Integer.parseInt(request.getParameter("perJudge"));
		int jdCount = Integer.parseInt(request.getParameter("jdCount"));
		int perJd = Integer.parseInt(request.getParameter("perJd"));
		int programCount = Integer.parseInt(request.getParameter("programCount"));
		int perProgram = Integer.parseInt(request.getParameter("perProgram"));
		int totalTime = Integer.parseInt(request.getParameter("totalTime"));
		int flag = Integer.parseInt(request.getParameter("flag"));
		TestSetUtil tsu = new TestSetUtil();
		TestSet ts = new TestSet();
		ts.setCourse(course);
		ts.setSinChCount(singleCount);
		ts.setPerSinScore(perSingle);
		ts.setMulChCount(multiplyCount);
		ts.setPerMulScore(perMultiply);
		ts.setJudgeCount(judgeCount);
		ts.setPerJudScore(perJudge);
		ts.setJdCount(jdCount);
		ts.setPerJdScore(perJd);
		ts.setProgramCount(programCount);
		ts.setPerProScore(perProgram);
		ts.setTotaltime(totalTime);
		//System.out.print(ts.getCourse()+","+flag);
		boolean success = tsu.insertOrUpdate(ts, flag);
		if(success)
		{
			response.sendRedirect(request.getContextPath()+"/admin/TestSuccess.html");
			
		}else
		{
			response.sendRedirect(request.getContextPath()+"/Fail.html");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {

	}

}
