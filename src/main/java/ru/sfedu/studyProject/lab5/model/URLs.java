package ru.sfedu.studyProject.lab5.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "lab5_url")
@Table(schema = "schema_lab5")
public class URLs {

    @Id
    @GeneratedValue(generator = "urlGenerator")
    @GenericGenerator(name = "urlGenerator",
    strategy = "foreign",
    parameters = @org.hibernate.annotations.Parameter (name = "property", value = "customer"))
    private long id;
    @OneToOne()
    @PrimaryKeyJoinColumn
    private Customer customer;
    private String url;

    public URLs(){};

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "URLs{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URLs urLs = (URLs) o;
        return id == urLs.id && Objects.equals(customer, urLs.customer) && Objects.equals(url, urLs.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, url);
    }
}
