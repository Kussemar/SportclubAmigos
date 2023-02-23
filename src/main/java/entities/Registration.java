package entities;

import java.util.Objects;

public class Registration {
    private Member member;
    private Team team;
    private int price;

    public Registration(Member member, Team team, int price){
    this.member = member;
    this.team = team;
    this.price = price;
    }

    public Member getMember() {
        return member;
    }

    public Team getTeam() {
        return team;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "member=" + member +
                ", team=" + team +
                ", price=" + price +
                '}';
    }

}
