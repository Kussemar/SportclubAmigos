import entities.Counter;
import entities.Member;
import entities.Registration;
import persistence.Database;
import persistence.MainSupport;
import persistence.MemberMapper;
import persistence.RegistrationMapper;

import java.util.List;

public class Main {

    private final static String USER = "root";
    private final static String PASSWORD = "mysql123";
    private final static String URL = "jdbc:mysql://localhost:3306/sportsclub?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true";

    public static void main(String[] args) {

        Database db = new Database(USER, PASSWORD, URL);
        MemberMapper memberMapper = new MemberMapper(db);
        List<Member> members = memberMapper.getAllMembers();
        List<Counter> counters = memberMapper.numbersOfParticipantsOnEachTeam();
        List<Counter> counters2 = memberMapper.numbersOfParticipantsOnEachSport();
        List<Counter> counters3 = memberMapper.menAndWomanCount();
        RegistrationMapper registrationMapper = new RegistrationMapper(db);
        List<Registration> registrations = registrationMapper.getAllRegistration();

        for (Registration registration : registrations) {
            System.out.println(registration.getMember().getName() + ": " +
                    registration.getTeam().getSport().getSport() + ", pris: " +
                    registration.getPrice() + " kr. ");
        }

        MainSupport ms = new MainSupport();
        ms.showMembers(members);

        ms.showNumbersOfParticipantsOnEachTeam(counters);
        ms.showMemberById(memberMapper, 13);
        ms.numbersOfParticipantsOnEachSport(counters2);
        ms.menAndWomanCount(counters3);

        /*  
            int newMemberId = insertMember(memberMapper);
            deleteMember(newMemberId, memberMapper);
            showMembers(members);
            updateMember(13, memberMapper);
        */
    }

    }



