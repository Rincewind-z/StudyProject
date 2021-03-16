package ru.sfedu.studyProject.lab5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "lab5_fursuit_part_details")
@Table(schema = "schema_lab5")
public class FurPartDetails {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private float progress;
    private double cost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "FurPartDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", progress=" + progress +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FurPartDetails that = (FurPartDetails) o;
        return id == that.id && Float.compare(that.progress, progress) == 0 && Double.compare(that.cost, cost) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, progress, cost);
    }
}
