package com.xihui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.xurui.dormain.*;
import com.xurui.util.*;

public class TestSubmitServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestSubmitServlet() {
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

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		String uid = (String)session.getAttribute("Uid");
		String course = (String)session.getAttribute("course");
		TestSetUtil testUtil = new TestSetUtil();
        TestSet ts = testUtil.getTestSet(course);
        TestUtil tu = new TestUtil();
        ArrayList al = new ArrayList();
		
        //ȡ��ѡ��
		for(int i=0;i<ts.getSinChCount();i++)
		{
			Test test = new Test();
			int qid = Integer.parseInt(request.getParameter("choiceId"+i));//�����ر�Ԫ����ȡ��������
			String stuAnswer = request.getParameter("choice"+i+"Value");//ȡ������
			stuAnswer = stuAnswer==null?"":stuAnswer;
			int tnumber = i;//���
			int type = 1;//������� 
			String answer = request.getParameter("choiceAnswer"+i);//ȡ��ȷ��
			int score = (answer.equalsIgnoreCase(stuAnswer))?ts.getPerSinScore():0;//���㿼���÷�
			
			test.setQid(qid);
			test.setUid(uid);
			test.setCourse(course);
			test.setScore(score);
			test.setStuAnswer(stuAnswer);
			test.setType(type);
			test.setTnumber(tnumber);
			
			al.add(test);
			
		}
		
		  //ȡ��ѡ��
		for(int i=0;i<ts.getMulChCount();i++)
		{
			Test test = new Test();
			int qid = Integer.parseInt(request.getParameter("mulchoiceId"+i));//�����ر�Ԫ����ȡ��������
			String[] paramValue = request.getParameterValues("mulchoice"+i+"Value");//ȡ������
			String stuAnswer="";
			if(paramValue!=null)
			{
				for(int j = 0;j < paramValue.length;j++)
				{
					stuAnswer+=paramValue[j];
				}
			}
			int tnumber = i;//���
			int type = 2;//������� 
			String answer = request.getParameter("mulchoiceAnswer"+i);//ȡ��ȷ��
			int score = (answer.equalsIgnoreCase(stuAnswer))?ts.getPerMulScore():0;//���㿼���÷�
			
			//System.out.println(qid+":"+stuAnswer+"/"+answer+":"+score);
			test.setQid(qid);
			test.setUid(uid);
			test.setCourse(course);
			test.setScore(score);
			test.setStuAnswer(stuAnswer);
			test.setType(type);
			test.setTnumber(tnumber);
			
			al.add(test);
			
		}
		
		  //ȡ�ж���
		for(int i=0;i<ts.getJudgeCount();i++)
		{
			Test test = new Test();
			int qid = Integer.parseInt(request.getParameter("judgeId"+i));//�����ر�Ԫ����ȡ��������
			String stuAnswer = request.getParameter("judge"+i+"Value");//ȡ������
			stuAnswer = stuAnswer==null?"":stuAnswer;
			
			int tnumber = i;//���
			int type = 3;//������� 
			String answer = request.getParameter("judgeAnswer"+i);//ȡ��ȷ��
			int score = (answer.equalsIgnoreCase(stuAnswer))?ts.getPerJudScore():0;//���㿼���÷�
			
			//System.out.println(qid+":"+stuAnswer+"/"+answer+":"+score);
			test.setQid(qid);
			test.setUid(uid);
			test.setCourse(course);
			test.setScore(score);
			test.setStuAnswer(stuAnswer);
			test.setType(type);
			test.setTnumber(tnumber);
			
			al.add(test);
			
		}
		
		  //ȡ�����
		for(int i=0;i<ts.getJdCount();i++)
		{
			Test test = new Test();
			int qid = Integer.parseInt(request.getParameter("jdId"+i));//�����ر�Ԫ����ȡ��������
			String stuAnswer = request.getParameter("stuJdAnswer"+i);//ȡ������
			stuAnswer = stuAnswer==null?"":new String(stuAnswer.getBytes("iso8859-1"),"gbk").trim();
			int tnumber = i;//���
			int type = 4;//������� 
			
			//System.out.println(qid+":"+stuAnswer);
			test.setQid(qid);
			test.setUid(uid);
			test.setCourse(course);
			test.setStuAnswer(stuAnswer);
			test.setType(type);
			test.setTnumber(tnumber);
			
			al.add(test);
			
		}
		
		  //ȡ�����
		for(int i=0;i<ts.getProgramCount();i++)
		{
			Test test = new Test();
			int qid = Integer.parseInt(request.getParameter("proId"+i));//�����ر�Ԫ����ȡ��������
			String stuAnswer = request.getParameter("stuProAnswer"+i);//ȡ������
			stuAnswer = stuAnswer==null?"":new String(stuAnswer.getBytes("iso8859-1"),"gbk").trim();
			int tnumber = i;//���
			int type = 5;//������� 
			
			//System.out.println(qid+":"+stuAnswer);
			test.setQid(qid);
			test.setUid(uid);
			test.setCourse(course);
			test.setStuAnswer(stuAnswer);
			test.setType(type);
			test.setTnumber(tnumber);
			
			al.add(test);
			
		}
		try{
			boolean success = tu.insertTest(al);
			if(success)
			{
				 new UserFlagUtil().setFlag(uid, course);	//���ÿ��Ա�־��Ϣ
				 response.sendRedirect(request.getContextPath()+"/student/finish.jsp");
			}else
			{
				response.sendRedirect(request.getContextPath()+"/student/Fail.html");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/student/Fail.html");
			
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
