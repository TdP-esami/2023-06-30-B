package it.polito.tdp.exam.model;

import java.time.LocalDate;
import java.util.Objects;

public class PeopleSalary {
    private People player;
    private Double salary;

    public PeopleSalary(People player, Double salary) {
        this.player = player;
        this.salary = salary;
    }

    public People getPlayer() {
        return player;
    }

    public void setPlayer(People player) {
        this.player = player;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleSalary that = (PeopleSalary) o;
        return Objects.equals(player, that.player) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, salary);
    }

    @Override
    public String toString() {
        return player.toString();
    }
}
