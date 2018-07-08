package kh.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import kh.web.dao.AdminDAO;
import kh.web.dao.ReportDAO;
import kh.web.dto.AgeGraphDTO;
import kh.web.dto.GroupDTO;
import kh.web.dto.GroupMemberDTO;
import kh.web.dto.MeetingDTO;
import kh.web.dto.MemberDTO;
import kh.web.dto.ReportDTO;
import kh.web.utils.DBUtils;

@WebServlet("*.ao")
public class AdminController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();
			String command = requestURI.substring(contextPath.length());

			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");
			PrintWriter out = response.getWriter();

			System.out.println("get : " + command);

			AdminDAO adao = new AdminDAO();
			ReportDAO rdao = new ReportDAO();
			boolean isAjax = false;
			boolean isRedirect = false;
			String dst = null;

			String adminId = (String) request.getSession().getAttribute("adminId");
			System.out.println("adminId: " + adminId);
			if (adminId == null) {
				isAjax = false;
				isRedirect = false;
				dst = "loginForm.jsp";
			} else {
				if (command.equals("/admin/login.ao")) {
					isAjax = false;
					isRedirect = false;
					dst = "loginForm.jsp";

				} else if (command.equals("/admin/logout.ao")) {
					request.getSession().invalidate();
					isAjax = false;
					isRedirect = false;
					dst = "login.ao";
				} else if (command.equals("/admin/main.ao")) {
					int reportAll = rdao.getReportCount("all");
					int reportToday = rdao.getReportCount("today");
					int memberAll = adao.getMemberCount("all");
					int memberToday = adao.getMemberCount("today");
					int groupAll = adao.getGroupCount("all");
					int groupToday = adao.getGroupCount("today");
					int meetingAll = adao.getMeetingCount("all");
					int meetingToday = adao.getMeetingCount("today");

					request.setAttribute("reportAll", reportAll);
					request.setAttribute("reportToday", reportToday);
					request.setAttribute("memberAll", memberAll);
					request.setAttribute("memberToday", memberToday);
					request.setAttribute("groupAll", groupAll);
					request.setAttribute("groupToday", groupToday);
					request.setAttribute("meetingAll", meetingAll);
					request.setAttribute("meetingToday", meetingToday);
					isAjax = false;
					isRedirect = false;
					dst = "mainpage.jsp";

				} else if (command.equals("/admin/member.ao")) {
					String text = request.getParameter("text");
					String subject = request.getParameter("subject");

					int currentPage = 0;
					String currentPageString = request.getParameter("currentPage");

					if (currentPageString == null) {
						currentPage = 1;
					} else {
						currentPage = Integer.parseInt(currentPageString);
					}

					List<MemberDTO> list = adao.memberList(currentPage * 10 - 9, currentPage * 10, subject, text);

					String page = adao.getMemberPageNavi(currentPage, subject, text);
					request.setAttribute("list", list);
					request.setAttribute("page", page);

					isAjax = false;
					isRedirect = false;
					dst = "member/member.jsp";

				} else if (command.equals("/admin/meeting.ao")) {
					String text = request.getParameter("text");
					String subject = request.getParameter("subject");

					System.out.println("controller-text: " + text);
					System.out.println("controller-subject: " + subject);
					int currentPage = 0;
					String currentPageString = request.getParameter("currentPage");
					if (currentPageString == null) {
						currentPage = 1;
					} else {
						currentPage = Integer.parseInt(currentPageString);
					}

					List<MeetingDTO> list = adao.allMeetingList(currentPage * 10 - 9, currentPage * 10, subject, text);
					String page = adao.getMeetingPageNavi(currentPage, subject, text);

					request.setAttribute("list", list);
					request.setAttribute("page", page);
					isAjax = false;
					isRedirect = false;
					dst = "meeting/meeting.jsp";

				} else if (command.equals("/admin/group.ao")) {
					String text = request.getParameter("text");
					String subject = request.getParameter("subject");

					System.out.println("controller-text: " + text);
					System.out.println("controller-subject: " + subject);
					int currentPage = 0;
					String currentPageString = request.getParameter("currentPage");
					if (currentPageString == null) {
						currentPage = 1;
					} else {
						currentPage = Integer.parseInt(currentPageString);
					}

					List<GroupDTO> list = adao.allGroupList(currentPage * 10 - 9, currentPage * 10, subject, text);
					String page = adao.getGroupPageNavi(currentPage, subject, text);
					System.out.println("group.ao_page:" + page);

					request.setAttribute("list", list);
					request.setAttribute("page", page);
					isAjax = false;
					isRedirect = false;
					dst = "group/group.jsp";

				} else if (command.equals("/admin/memberpage.ao")) {
					String member_email = request.getParameter("member_email");
					System.out.println(member_email);
					MemberDTO mdto = adao.getMember(member_email);
					ReportDTO rdto = adao.reportMemJoin(member_email);

					Map<String, Object> map = adao.memGroupMemJoin(mdto.getMember_name());

					List mlist = (List) map.get("mlist");
					List gmlist = (List) map.get("gmlist");

					if (mlist.size() != 0) {
						MemberDTO mlistdto = (MemberDTO) mlist.get(0);
						System.out.println("map 출력: " + mlist.get(0));
						request.setAttribute("mlistdto", mlistdto);
						System.out.println("mlistdto:" + mlistdto.getMember_interests());
					}
					if (gmlist.size() != 0) {
						// System.out.println("map 출력: " + gmlist.get(0));
						GroupMemberDTO gmlistdto = (GroupMemberDTO) gmlist.get(0);
						request.setAttribute("gmdto", gmlistdto);
						System.out.println("gmlistdto:" + gmlistdto.getGroup_name());
					}
					request.setAttribute("mdto", mdto);
					request.setAttribute("rdto", rdto);
					isAjax = false;
					isRedirect = false;
					dst = "member/memberpage.jsp";

				} else if (command.equals("/admin/grouppage.ao")) {
					int group_seq = Integer.parseInt(request.getParameter("group_seq"));
					GroupDTO gdto = new GroupDTO();
					gdto = adao.getGroupData(group_seq);
					List<GroupMemberDTO> list = new ArrayList<>();
					list = adao.getGroupMember(group_seq);
					int size = list.size();
					System.out.println("groupmember_size: " + list.size());

					ReportDTO rdto = rdao.reportGroupJoin(group_seq);

					request.setAttribute("size", size);
					request.setAttribute("gdto", gdto);
					request.setAttribute("rdto", rdto);
					isAjax = false;
					isRedirect = false;
					dst = "group/grouppage.jsp?group_seq='" + group_seq + "'";

				} else if (command.equals("/admin/report.ao")) {
					isAjax = false;
					isRedirect = false;
					dst = "memberreport.ao";

				} else if (command.equals("/admin/memberreport.ao")) {
					String text = request.getParameter("text");
					String subject = request.getParameter("subject");

					System.out.println("controller-text: " + text);
					System.out.println("controller-subject: " + subject);
					int currentPage = 0;
					String currentPageString = request.getParameter("currentPage");
					if (currentPageString == null) {
						currentPage = 1;
					} else {
						currentPage = Integer.parseInt(currentPageString);
					}

					List<ReportDTO> list = rdao.memberReportList(currentPage * 10 - 9, currentPage * 10, subject, text);
					String page = rdao.memberReportPageNavi(currentPage, subject, text);

					request.setAttribute("list", list);
					request.setAttribute("page", page);

					// List<ReportDTO> list = new ArrayList<>();
					// list = rdao.getAllReport("member");
					// request.setAttribute("list", list);
					isAjax = false;
					isRedirect = false;
					dst = "report/memberreport.jsp";

				} else if (command.equals("/admin/modal.ao")) {
					String distinction = request.getParameter("distinction");
					String member_email = request.getParameter("member_email");
					int report_seq = Integer.parseInt(request.getParameter("report_seq"));
					String group_name = request.getParameter("group_name");

					System.out.println("distinction: " + distinction);
					System.out.println("member_email:" + member_email);
					System.out.println("report_seq:" + report_seq);

					JSONObject json = new JSONObject();

					MemberDTO mdto = adao.getMember(member_email);
					ReportDTO rdto = new ReportDTO();
					GroupDTO gdto = new GroupDTO();

					if (distinction.equals("member")) {
						rdao.modifyMemberReportState(member_email, report_seq);

					} else {
						System.out.println("modal.ao-group:" + group_name + "," + report_seq);
						rdao.modifyGroupReportState(group_name, report_seq);
					}

					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");

					response.getWriter().print(json);
					response.getWriter().flush();
					response.getWriter().close();

					isAjax = true;

				} else if (command.equals("/admin/groupreport.ao")) {
					String text = request.getParameter("text");
					String subject = request.getParameter("subject");

					System.out.println("controller-text: " + text);
					System.out.println("controller-subject: " + subject);
					int currentPage = 0;
					String currentPageString = request.getParameter("currentPage");
					if (currentPageString == null) {
						currentPage = 1;
					} else {
						currentPage = Integer.parseInt(currentPageString);
					}

					List<ReportDTO> list = rdao.groupReportList(currentPage * 10 - 9, currentPage * 10, subject, text);
					String page = rdao.groupReportPageNavi(currentPage, subject, text);

					request.setAttribute("list", list);
					request.setAttribute("page", page);

					isAjax = false;
					isRedirect = false;
					dst = "report/groupreport.jsp";

				} else if (command.equals("/admin/warning.ao")) {
					JSONObject json = new JSONObject();

					if (request.getParameter("distinction").equals("member")) {
						System.out.println("memberwarning");
						String member_email = request.getParameter("member_email");

						int result = adao.plusMemberWarningNumber(member_email);
						MemberDTO mdto = adao.getMember(member_email);

						String warningdate = mdto.getMember_warningdate();
						String expiredate = mdto.getMember_expiredate();
						int warningnumber = mdto.getMember_warningnumber();
						int betweendate = mdto.getMember_betweendate();

						if (result > 0) {
							json.put("betweendate", betweendate);
							json.put("warningnumber", warningnumber);
							json.put("warningdate", warningdate);
							json.put("expiredate", expiredate);
						}

					} else if (request.getParameter("distinction").equals("group")) {
						System.out.println("groupwarning");
						// int group_seq = Integer.parseInt(request.getParameter("group_seq"));
						String group_name = request.getParameter("group_name");
						System.out.println("warning-group:" + group_name);

						int result = adao.plusGroupWarningNumber(group_name);
						GroupDTO gdto = adao.getGroupDataByName(group_name);

						String warningdate = gdto.getGroup_warningdate();
						String expiredate = gdto.getGroup_expiredate();
						int warningnumber = gdto.getGroup_warningnumber();
						int betweendate = gdto.getGroup_betweendate();

						if (result > 0) {
							json.put("betweendate", betweendate);
							json.put("warningnumber", warningnumber);
							json.put("warningdate", warningdate);
							json.put("expiredate", expiredate);
						}
					}

					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");

					response.getWriter().print(json);
					response.getWriter().flush();
					response.getWriter().close();

					isAjax = true;
				} else if (command.equals("/admin/search.ao")) {
					String subject = request.getParameter("subject");
					String text = request.getParameter("text");

					System.out.println("subject : " + subject);
					System.out.println("text : " + text);
					List<MemberDTO> mlist = new ArrayList<>();
					List<GroupDTO> glist = new ArrayList<>();
					List<MeetingDTO> meetlist = new ArrayList<>();

					if (request.getParameter("distinction").equals("member")) {
						int currentPage = 0;
						String currentPageString = request.getParameter("currentPage");

						if (currentPageString == null) {
							currentPage = 1;
						} else {
							currentPage = Integer.parseInt(currentPageString);
						}
						System.out.println("currentpage: " + currentPage);
						System.out.println("currentpageString: " + currentPageString);

						mlist = adao.memberList(currentPage * 10 - 9, currentPage * 10, subject, text);

						System.out.println("list.size(): " + mlist.size());
						String page = adao.getMemberPageNavi(currentPage, subject, text);

						for (int i = 0; i < mlist.size(); i++) {
							System.out.println("controller-search.ao:" + mlist.get(i).getMember_email());
						}
						System.out.println("controller-page:" + page);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");

						Map<String, Object> map = new HashMap<>();
						map.put("mlist", mlist);
						map.put("page", page);

						new Gson().toJson(map, response.getWriter());

						isAjax = true;

					} else if (request.getParameter("distinction").equals("group")) {

						int currentPage = 0;
						String currentPageString = request.getParameter("currentPage");

						if (currentPageString == null) {
							currentPage = 1;
						} else {
							currentPage = Integer.parseInt(currentPageString);
						}
						System.out.println("currentpage: " + currentPage);
						System.out.println("currentpageString: " + currentPageString);

						glist = adao.allGroupList(currentPage * 10 - 9, currentPage * 10, subject, text);

						System.out.println("list.size(): " + mlist.size());
						String page = adao.getGroupPageNavi(currentPage, subject, text);

						for (int i = 0; i < glist.size(); i++) {
							System.out.println("controller-search.ao:" + glist.get(i).getGroup_name());
						}
						System.out.println("controller-page:" + page);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");

						Map<String, Object> map = new HashMap<>();
						map.put("glist", glist);
						map.put("page", page);

						new Gson().toJson(map, response.getWriter());

						isAjax = true;

					} else if (request.getParameter("distinction").equals("meeting")) {
						System.out.println("distinction:meeting");
						int currentPage = 0;
						String currentPageString = request.getParameter("currentPage");

						if (currentPageString == null) {
							currentPage = 1;
						} else {
							currentPage = Integer.parseInt(currentPageString);
						}
						System.out.println("currentpage: " + currentPage);
						System.out.println("currentpageString: " + currentPageString);

						meetlist = adao.allMeetingList(currentPage * 10 - 9, currentPage * 10, subject, text);

						System.out.println("list.size(): " + mlist.size());
						String page = adao.getMeetingPageNavi(currentPage, subject, text);

						for (int i = 0; i < meetlist.size(); i++) {
							System.out.println("controller-search.ao:" + meetlist.get(i).getMeeting_title());
						}
						System.out.println("controller-page:" + page);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");

						Map<String, Object> map = new HashMap<>();
						map.put("mlist", meetlist);
						map.put("page", page);

						new Gson().toJson(map, response.getWriter());

						isAjax = true;
					}
					isAjax = true;
				} else if (command.equals("/admin/email.ao")) {
					String distinction = request.getParameter("distinction");
					System.out.println("email.ao-disctinction:" + distinction);
					String email = "";
					String warningdate = "";
					String expiredate = "";

					if (distinction.equals("member")) {
						email = request.getParameter("member_email");
						warningdate = request.getParameter("member_warningdate");
						expiredate = request.getParameter("member_expiredate");
					} else {
						email = request.getParameter("group_leader");
						warningdate = request.getParameter("group_warningdate");
						expiredate = request.getParameter("group_expiredate");
					}

					String toEmail = email;
					String host = "smtp.naver.com";
					final String user = "sksksrff@naver.com";
					final String password = "rmancis1990!";

					String to = "sksksrff@gmail.com";// toEmail로 교체

					// Get the session object
					Properties props = new Properties();
					props.put("mail.smtp.host", host);
					props.put("mail.smtp.auth", "true");

					Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}
					});

					// Compose the message
					try {
						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress(user));
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

						// Subject
						message.setSubject("meetnow입니다.");

						// Text
						message.setText("다른 회원님에게서 신고를 당해 일주일간 블락입니다." + "회원님이 블락된 날짜 : " + warningdate
								+ "회원님의 블락 만료일 : " + expiredate);

						// send the message
						Transport.send(message);
						System.out.println("message sent successfully...");

					} catch (MessagingException e) {
						e.printStackTrace();
					}
					isAjax = true;
				} else if (command.equals("/admin/stats.ao")) {
					// 동호회카테고리별(파이), 신고내용별(막대) 방문자수(막대)
					AgeGraphDTO adto = adao.memberAgeGraph();
					
					request.setAttribute("adto", adto);
					
					isAjax = false;
					isRedirect = false;
					dst = "stats/stats.jsp";

				} else if (command.equals("/admin/getnum.ao")) {
					String distinction = request.getParameter("distinction");
					int report_seq = Integer.parseInt(request.getParameter("report_seq"));

					System.out.println("distinction: " + distinction);
					System.out.println("report_seq:" + report_seq);

					JSONObject json = new JSONObject();

					MemberDTO mdto = new MemberDTO();
					ReportDTO rdto = new ReportDTO();
					GroupDTO gdto = new GroupDTO();

					if (distinction.equals("member")) {
						mdto = rdao.memReportJoin(report_seq);
						json.put("warningnumber", String.valueOf(mdto.getMember_warningnumber()));
						System.out.println("warning_mdto, " + mdto.getMember_email() + " warningnumber:"
								+ mdto.getMember_warningnumber());

					} else {
						gdto = rdao.groupReportJoin(report_seq);
						json.put("warningnumber", String.valueOf(gdto.getGroup_warningnumber()));
						System.out.println("warning_gdto :" + gdto.getGroup_name() + " warningnumber:"
								+ mdto.getMember_warningnumber());
					}

					rdto = rdao.getReportData(report_seq);
					json.put("date", rdto.getReport_date());
					json.put("caller", rdto.getReport_caller());
					json.put("callee", rdto.getReport_calleemember());
					json.put("reason", rdto.getReport_reason());

					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");

					response.getWriter().print(json);
					response.getWriter().flush();
					response.getWriter().close();

					isAjax = true;
				}

			}

			if (isAjax) {

			} else {

				if (isRedirect) {
					response.sendRedirect(dst);
				} else {
					RequestDispatcher rd = request.getRequestDispatcher(dst);
					rd.forward(request, response);
				}
				// response.getWriter().append("Served at: ").append(request.getContextPath());

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.html");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");

			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();
			String command = requestURI.substring(contextPath.length());

			response.setCharacterEncoding("utf8");
			PrintWriter out = response.getWriter();

			System.out.println("post : " + command);

			ReportDAO rdao = new ReportDAO();
			AdminDAO adao = new AdminDAO();
			boolean isRedirect = true;
			String dst = null;

			if (command.equals("/admin/login.ao")) {
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				System.out.println("id : " + id);
				System.out.println("pw : " + pw);
				boolean result = adao.loginAdmin(id, pw);
				System.out.println(result);

				if (result) {
					request.getSession().setAttribute("adminId", id);

					int reportAll = rdao.getReportCount("all");
					int reportToday = rdao.getReportCount("today");
					int memberAll = adao.getMemberCount("all");
					int memberToday = adao.getMemberCount("today");
					int groupAll = adao.getGroupCount("all");
					int groupToday = adao.getGroupCount("today");
					int meetingAll = adao.getMeetingCount("all");
					int meetingToday = adao.getMeetingCount("today");

					request.setAttribute("reportAll", reportAll);
					request.setAttribute("reportToday", reportToday);
					request.setAttribute("memberAll", memberAll);
					request.setAttribute("memberToday", memberToday);
					request.setAttribute("groupAll", groupAll);
					request.setAttribute("groupToday", groupToday);
					request.setAttribute("meetingAll", meetingAll);
					request.setAttribute("meetingToday", meetingToday);

					isRedirect = false;
					dst = "mainpage.jsp";
				} else {
					isRedirect = true;
					dst = "loginForm.jsp";
				}
			}

			if (isRedirect == false) {
				RequestDispatcher rd = request.getRequestDispatcher(dst);
				rd.forward(request, response);
			} else {
				response.sendRedirect(dst);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.html");
		}

	}
	
	

}
