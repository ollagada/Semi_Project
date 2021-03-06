package kh.web.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kh.web.dto.GroupDTO;
import kh.web.dto.GroupMemberDTO;
import kh.web.dto.GroupPicDTO;
import kh.web.dto.MeetingDTO;
import kh.web.dto.MemberCountDTO;
import kh.web.dto.MygroupDTO;
import kh.web.utils.DBUtils;

public class GroupDAO {
	public List<GroupDTO> allgroups() throws Exception {
		Connection con = DBUtils.getConnection();
		String sql = "select * from create_group";
		PreparedStatement pstat = con.prepareStatement(sql);

		ResultSet rs = pstat.executeQuery();
		List<GroupDTO> result = new ArrayList<>();
		
		while(rs.next()) {
			GroupDTO dto = new GroupDTO();
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setGroup_leader(rs.getString("group_leader"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setGroup_location(rs.getString("group_location"));
			dto.setGroup_interests(rs.getString("group_interests"));
			dto.setGroup_info(rs.getString("group_info"));
			dto.setGroup_picture(rs.getString("group_picture"));
			
			result.add(dto);
		}

		con.close();
		rs.close();
		pstat.close();

		return result;
	}
	
	public List<GroupPicDTO> allgroupsPictures() throws Exception {
		Connection con = DBUtils.getConnection();
		String sql = "select * from group_picture";
		PreparedStatement pstat = con.prepareStatement(sql);

		ResultSet rs = pstat.executeQuery();
		List<GroupPicDTO> result = new ArrayList<>();
		
		while(rs.next()) {
			GroupPicDTO dto = new GroupPicDTO();
			
			dto.setGroup_picture_seq(rs.getInt("group_picture_seq"));
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setOriginal_name(rs.getString("original_name"));
			dto.setSystem_name(rs.getString("system_name"));
			
			result.add(dto);

		}

		con.close();
		rs.close();
		pstat.close();

		return result;
	}
	
	public List<MygroupDTO> myGroupList(String member_email) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select mygroup_seq, system_name, group_name ,group_picture.group_seq "
				+ "from group_picture join mygroup on group_picture.group_seq = mygroup.group_seq "
				+ "where mygroup.member_email=? order by mygroup_seq";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setString(1, member_email);
		
		ResultSet rs = pstat.executeQuery();

		List<MygroupDTO> result = new ArrayList<>();

		while(rs.next()) {
			MygroupDTO dto = new MygroupDTO();

			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setSystem_name(rs.getString("system_name"));

			result.add(dto);
		}

		con.close();
		rs.close();
		pstat.close();

		return result;
	}

	public MemberCountDTO MemberCount(int groupSeq) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select DISTINCT GROUP_SEQ, (select count(*) from group_member where GROUP_SEQ = ?) count from group_member where GROUP_SEQ = ? ";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, groupSeq);
		pstat.setInt(2, groupSeq);

		ResultSet rs = pstat.executeQuery();
		List<MemberCountDTO> result = new ArrayList<>();
		MemberCountDTO dto = null;

		if(rs.next()) {
			dto = new MemberCountDTO();
			
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setCount(rs.getInt(2));

		}

		con.close();
		rs.close();
		pstat.close();

		return dto;

	}
	public List<String> DistanceSearch(String lat, String lng, String distance) throws Exception{

		Connection con = DBUtils.getConnection();
		
		double latitude = Double.parseDouble(lat);
		
		double longitude =Double.parseDouble(lng);
		
		String sql = "select group_name, group_lat, group_lng from create_group";
		PreparedStatement pstat = con.prepareStatement(sql);
		
		ResultSet rs = pstat.executeQuery();
		List<String> fiveList = new ArrayList<>();
		List<String> tenList = new ArrayList<>();
		List<String> fifteenList = new ArrayList<>();
		List<String> allList = new ArrayList<>();
		System.out.println(1);
		while(rs.next()) {

			String dbGroupName = rs.getString("group_name");
			double dbGroupLat = Double.parseDouble(rs.getString("group_lat"));
			double dbGroupLng = Double.parseDouble(rs.getString("group_lng"));
			System.out.println(11);
			double theta = longitude - dbGroupLng;
			double dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(dbGroupLat)) + Math.cos(deg2rad(latitude))
			*Math.cos(deg2rad(dbGroupLat)) * Math.cos(deg2rad(theta));

			dist = Math.acos(dist);
			dist = rad2deg(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344; //km일때
			//		 	 dist = dist * 1609.344; meter 일때
			
			if(dist <= 5) {
				fiveList.add(dbGroupName);
			}
			if(dist <= 10) {
				tenList.add(dbGroupName);
			}
			if(dist <= 15) {
				fifteenList.add(dbGroupName);
			}
			if(dist != 0) {
				allList.add(dbGroupName);
			}
			
		}
		
		if(distance.equals("5")) {
			
			return fiveList;
		}else if(distance.equals("10")) {
			
			
			return tenList;
		}else if(distance.equals("15")) {
			
			return fifteenList;
		}
		else {
			return allList;
		}
		
	}
	
	public List<GroupDTO> groupInfo(String seq) throws Exception{
		int group_seq = Integer.parseInt(seq);
		
		Connection con = DBUtils.getConnection();
		String sql = "select * from create_group where GROUP_SEQ=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, group_seq);
		ResultSet rs = pstat.executeQuery();
		
		List<GroupDTO> result = new ArrayList<>();
		
		if(rs.next()) {
			GroupDTO dto = new GroupDTO();
			
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setGroup_leader(rs.getString("group_leader"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setGroup_location(rs.getString("group_location"));
			dto.setGroup_interests(rs.getString("group_interests"));
			dto.setGroup_info(rs.getString("group_info"));
			dto.setGroup_picture(rs.getString("group_picture"));
			
			result.add(dto);
		}
		
		con.close();
		pstat.close();
		rs.close();
		
		return result;
	}
	
	 public double deg2rad(double deg){  
	      return (double)(deg * Math.PI / (double)180d);  
	   }  
	   public double rad2deg(double rad) {
	      return (double)(rad*(double)180d / Math.PI);
	   }
	public int insertGroup(GroupDTO dto) throws Exception{
		Connection con = DBUtils.getConnection();
		
		String sql = "insert into create_group values(group_seq.nextval,?,?,?,?,?,'default.jpg')";
		PreparedStatement  psat = con.prepareStatement(sql);
		psat.setString(1,dto.getGroup_leader());
		psat.setString(2, dto.getGroup_name());
		psat.setString(3, dto.getGroup_location());
		psat.setString(4, dto.getGroup_interests());
		psat.setString(5, dto.getGroup_info());
		int result = psat.executeUpdate();

		con.commit();
		con.close();
		psat.close();
		
		
		return result;
		
		
	}
	public String printNameGroup(String groupTitle) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select group_name from create_group where group_name=?";
		PreparedStatement psat = con.prepareStatement(sql);
		psat.setString(1, groupTitle);
		
		ResultSet rs = psat.executeQuery();
		rs.next();
		String groupName=rs.getString("group_name");
		System.out.println("그룹이름:"+groupName);
		rs.close();
		psat.close();
		con.close();
		
		return (String) groupName;
		
		
	}
	public List<MeetingDTO> nextMeetup(int groupSeq,int meeting_seq,String msg) throws Exception{
		Connection con = DBUtils.getConnection();
		PreparedStatement pstat = null;
		if(meeting_seq == 0 && msg.equals("one")) {
			String sql = "select *from (select meeting.*, row_number()over(partition by group_seq order by meeting_start_time asc) rn "
					+ "from meeting where group_seq=? and meeting_start_time > sysdate) where rn = 1";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, groupSeq);
		}else if(groupSeq == 0 && msg.equals("pre")) {
			String sql = "select * from meeting where group_seq=3 and meeting_start_time > sysdate and meeting_seq != ?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, meeting_seq);
		}else if(msg.equals("all")){
			String sql = "select * from meeting where group_seq=? and meeting_start_time > sysdate";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, groupSeq);
		}
		
		ResultSet rs = pstat.executeQuery();
		
		List<MeetingDTO> result = new ArrayList<>();
		if(rs.next()) {
			MeetingDTO dto = new MeetingDTO();
			
			dto.setMeeting_seq(rs.getInt("meeting_seq"));
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setGroup_leader(rs.getString("group_leader"));
			dto.setMeeting_title(rs.getString("meeting_title"));
			dto.setMeeting_contents(rs.getString("meeting_contents"));
			dto.setMeeting_start_time(rs.getDate("meeting_start_time"));
			dto.setMeeting_end_time(rs.getDate("meeting_end_time"));
			dto.setMeeting_location(rs.getString("meeting_location"));
			dto.setMeeting_picture(rs.getString("meeting_picture"));
			
			result.add(dto);
		}
		
		con.close();
		pstat.close();
		rs.close();
		
		
		return result;
	}
	
	public List<MeetingDTO> lastMeeting(int groupSeq) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select * from meeting where group_seq=? and meeting_start_time < sysdate";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, groupSeq);
		ResultSet rs = pstat.executeQuery();
		
		List<MeetingDTO> result = new ArrayList<>();
		while(rs.next()) {
			MeetingDTO dto = new MeetingDTO();
			
			dto.setMeeting_seq(rs.getInt("meeting_seq"));
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setGroup_leader(rs.getString("group_leader"));
			dto.setMeeting_title(rs.getString("meeting_title"));
			dto.setMeeting_contents(rs.getString("meeting_contents"));
			dto.setMeeting_start_time(rs.getDate("meeting_start_time"));
			dto.setMeeting_end_time(rs.getDate("meeting_end_time"));
			dto.setMeeting_location(rs.getString("meeting_location"));
			dto.setMeeting_picture(rs.getString("meeting_picture"));
			
			result.add(dto);
		}
		
		con.close();
		pstat.close();
		rs.close();
		
		return result;
	}
	
	public boolean isGroupMember(int group_seq, String member_email) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select * from mygroup where member_email=? and group_seq=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setString(1, member_email);
		pstat.setInt(2, group_seq);
		
		ResultSet rs = pstat.executeQuery();
		
		if(rs.next()) {
			return true;
		}
		
		return false;
	}
	
	public int joinGroup(String member_email, int group_seq, String group_name) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "insert into mygroup values(mygroup_seq.nextval,?,?,?)";
		PreparedStatement pstat = con.prepareStatement(sql);
		
		
		pstat.setInt(1, group_seq);
		pstat.setString(2, group_name);
		pstat.setString(3, member_email);
		
		int result = pstat.executeUpdate();
		
		con.commit();
		con.close();
		pstat.close();
		
		return result;
	}
	
	public int groupMemberOut(int group_seq,String member_email) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "delete from mygroup where member_email=? and group_seq=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		
		pstat.setString(1, member_email);
		pstat.setInt(2, group_seq);
		
		int result = pstat.executeUpdate();
		
		con.commit();
		con.close();
		pstat.close();
		
		return result;
	}
	
	public List<GroupMemberDTO> memberList(int group_seq) throws Exception{
		Connection con = DBUtils.getConnection();
		String sql = "select gm.*, m.member_picture, g.group_leader from group_member gm, member m, create_group g "
				+ "where gm.member_name=m.member_name and gm.group_seq = g.group_seq and gm.group_seq=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, group_seq);
		
		ResultSet rs = pstat.executeQuery();
		
		List<GroupMemberDTO> result = new ArrayList<>();
		
		while(rs.next()) {
			GroupMemberDTO dto = new GroupMemberDTO();
			
			dto.setGroup_member_seq(rs.getInt("group_member_seq"));
			dto.setMember_name(rs.getString("member_name"));
			dto.setGroup_seq(rs.getInt("group_seq"));
			dto.setJoin_date(rs.getString("join_date"));
			dto.setGroup_name(rs.getString("group_name"));
			dto.setMember_picture(rs.getString("member_picture"));
			dto.setGroup_leader(rs.getString("group_leader"));
			
			result.add(dto);
		}
		
		con.close();
		pstat.close();
		rs.close();
		
		return result;
		
	}
	
}









