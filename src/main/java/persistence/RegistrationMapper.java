package persistence;

import entities.Member;
import entities.Registration;
import entities.Sport;
import entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationMapper {
    private Database database;

    public RegistrationMapper(Database database) {
        this.database = database;
    }

    public List<Registration> getAllRegistration() {

        List<Registration> registrationList = new ArrayList<>();

        String sql = "SELECT * FROM club_view;";

        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // Member object creation
                    int memberId = rs.getInt("member_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    int zip = rs.getInt("zip");
                    String city = rs.getString("city");
                    String gender = rs.getString("gender");
                    int year = rs.getInt("year");
                    Member member = new Member(memberId, name, address, zip, city, gender, year);
                    // Sport object creation
                    int sportId = rs.getInt("sport_id");
                    String sportName = rs.getString("sport");
                    Sport sport = new Sport(sportId, sportName);
                    // Team object creation
                    String teamId = rs.getString("team_id");
                    // Registration object creation
                    Team team = new Team(teamId, sport);
                    int price = rs.getInt("price");
                    Registration registration = new Registration(member,team,price);
                    registrationList.add(registration);
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return registrationList;
    }
}
