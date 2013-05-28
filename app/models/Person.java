package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.EnumType.ORDINAL;

@Entity
public class Person extends Model {
    public static Finder<Integer, Person> find = new Finder<>(Integer.class, Person.class);

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Enumerated(value = ORDINAL)
    private Gender gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
