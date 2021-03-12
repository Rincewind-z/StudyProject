package ru.sfedu.studyProject.lab2;

import org.simpleframework.xml.Attribute;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class TestEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Date dateCreated;
    @Column(name = "MEOW")
    private Boolean check;
    @Embedded
    private MyClass polyshko;

    public Long getId() {
        return id;
    }



    public void setPolyshko(MyClass polyshko) {
        this.polyshko = polyshko;
    }

    public MyClass getPolyshko() {
        return polyshko;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }


    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", check=" + check +
                ", polyshko=" + polyshko +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(check, that.check) && Objects.equals(polyshko, that.polyshko);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dateCreated, check, polyshko);
    }
}
