package ru.sfedu.studyProject.lab2;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MyClass {
    private String pole;

    public void setPole(String pole) {
        this.pole = pole;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "pole='" + pole + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyClass myClass = (MyClass) o;
        return Objects.equals(pole, myClass.pole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pole);
    }

    public String getPole() {
        return pole;
    }
}
