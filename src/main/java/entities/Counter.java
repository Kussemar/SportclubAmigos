package entities;

public class Counter {
    private  String teamId;
    private String sport;
    private int numbersOfRegistrations;

    public Counter(String teamId, String sport, int numbersOfRegistrations) {
        this.teamId = teamId;
        this.sport = sport;
        this.numbersOfRegistrations = numbersOfRegistrations;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getNumbersOfRegistrations() {
        return numbersOfRegistrations;
    }

    public void setNumbersOfRegistrations(int numbersOfRegistrations) {
        this.numbersOfRegistrations = numbersOfRegistrations;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "teamId='" + teamId + '\'' +
                ", sport='" + sport + '\'' +
                ", numbersOfRegistrations=" + numbersOfRegistrations +
                '}';
    }
}
