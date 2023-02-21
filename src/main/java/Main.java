import entities.Counter;
import entities.Member;
import persistence.Database;
import persistence.MainSupport;
import persistence.MemberMapper;

import java.util.List;

import static persistence.MainSupport.showNumbersOfParticipantsOnEachTeam;

public class Main {

    private final static String USER = "root";
    private final static String PASSWORD = "DEMS1217";
    private final static String URL = "jdbc:mysql://localhost:3306/sportsclub?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true";

    public static void main(String[] args) {

        Database db = new Database(USER, PASSWORD, URL);
        MemberMapper memberMapper = new MemberMapper(db);
        List<Member> members = memberMapper.getAllMembers();
        List<Counter> counters = memberMapper.numbersOfParticipantsOnEachTeam();
        MainSupport ms = new MainSupport();
        ms.showNumbersOfParticipantsOnEachTeam(counters);
        ms.showMembers(members);
        ms.showMemberById(memberMapper, 13);

        /*  
            int newMemberId = insertMember(memberMapper);
            deleteMember(newMemberId, memberMapper);
            showMembers(members);
            updateMember(13, memberMapper);
        */
    }

    }



