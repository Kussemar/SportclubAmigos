package persistence;

import entities.Counter;
import entities.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberMapper {

        private Database database;

        public MemberMapper(Database database) {
            this.database = database;
        }

        public List<Member> getAllMembers() {

            List<Member> memberList = new ArrayList<>();

            String sql = "select member_id, name, address, m.zip, gender, city, year " +
                         "from member m inner join zip using(zip) " +
                         "order by member_id";

            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        int memberId = rs.getInt("member_id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        int zip = rs.getInt("zip");
                        String city = rs.getString("city");
                        String gender = rs.getString("gender");
                        int year = rs.getInt("year");
                        memberList.add(new Member(memberId, name, address, zip, city, gender, year));
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return memberList;
        }

    public List<Counter> numbersOfParticipantsOnEachTeam() {

        List<Counter> numbersOfParticipantsOnEachTeamList = new ArrayList<>();

        String sql = "select r.team_id, s.sport, count(r.member_id) as numberOfRegistrations \n" +
                "from registration r \n" +
                "inner join team t\n" +
                "on r.team_id = t.team_id\n" +
                "inner join sport s\n" +
                "on t.sport_id = s.sport_id\n" +
                "group by team_id;";

        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String teamId = rs.getString("team_id");
                    String sport = rs.getString("sport");
                    int numbersOfRegistrations = rs.getInt("numberOfRegistrations");
                    numbersOfParticipantsOnEachTeamList.add(new Counter(teamId, sport, numbersOfRegistrations));
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numbersOfParticipantsOnEachTeamList;
    }


    public List<Counter> numbersOfParticipantsOnEachSport() {

        List<Counter> numbersOfParticipantsOnEachSportList = new ArrayList<>();

        String sql = "select s.sport, count(r.member_id) as numberOfRegistrations \n" +
                "from registration r\n" +
                "inner join sport s\n" +
                "group by sport;";

        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String sport = rs.getString("sport");
                    int numbersOfRegistrations = rs.getInt("numberOfRegistrations");
                    numbersOfParticipantsOnEachSportList.add(new Counter(sport, numbersOfRegistrations));
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numbersOfParticipantsOnEachSportList;
    }
    public List<Counter> menAndWomanCount() {

        List<Counter> menAndWomanCountList = new ArrayList<>();

        String sql = "SELECT count(gender) \n" +
                "FROM `sportsclub`.`member`\n" +
                "group by gender;";

        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int count = rs.getInt("count(gender)");
                    menAndWomanCountList.add(new Counter(count));
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menAndWomanCountList;
    }

    public Member getMemberById(int memberId) {
            Member member = null;

            String sql =  "select member_id, name, address, m.zip, gender, city, year " +
            "from member m inner join zip using(zip) " +
            "where member_id = ?";

            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, memberId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        memberId = rs.getInt("member_id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        int zip = rs.getInt("zip");
                        String city = rs.getString("city");
                        String gender = rs.getString("gender");
                        int year = rs.getInt("year");
                        member = new Member(memberId, name, address, zip, city, gender, year);
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int a = 1;
            return member;
        }

        public boolean deleteMember(int member_id){
            boolean result = false;
            String sql = "delete from member where member_id = ?";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, member_id);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return result;
        }

        public Member insertMember(Member member){
            boolean result = false;
            int newId = 0;
            String sql = "insert into member (name, address, zip, gender, year) values (?,?,?,?,?)";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS )) {
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getAddress());
                    ps.setInt(3, member.getZip());
                    ps.setString(4, member.getGender());
                    ps.setInt(5, member.getYear());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                    ResultSet idResultset = ps.getGeneratedKeys();
                    if (idResultset.next()){
                        newId = idResultset.getInt(1);
                        member.setMemberId(newId);
                    } else {
                        member = null;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return member;
        }

        public boolean updateMember(Member member) {
            boolean result = false;
            String sql =    "update member " +
                            "set name = ?, address = ?, zip = ?, gender = ?, year = ? " +
                            "where member_id = ?";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getAddress());
                    ps.setInt(3, member.getZip());
                    ps.setString(4, member.getGender());
                    ps.setInt(5, member.getYear());
                    ps.setInt(6, member.getMemberId());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return result;
        }
}
