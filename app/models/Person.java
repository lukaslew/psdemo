package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Person extends Model {
    public static Finder<Integer, Person> find = new Finder<>(Integer.class, Person.class);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(value = ORDINAL)
    private Gender gender;

    public Person() {
        this(null, null);
    }

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Person other = (Person) obj;

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.gender, other.gender);
    }

    @Override
    public String toString() {
        return String.format("Person{id=%d, name='%s', gender=%s}",
                id, name, gender);
    }
}
