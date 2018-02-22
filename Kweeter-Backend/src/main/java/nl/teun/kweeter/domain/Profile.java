package nl.teun.kweeter.domain;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "Profile.all", query = "SELECT p FROM Profile p "),
        @NamedQuery(name = "Profile.findbyid", query = "SELECT p FROM Profile p WHERE p.id = :p_id"),
        @NamedQuery(name = "Profile.findbyemail", query = "SELECT p FROM Profile p WHERE p.email = :p_email"),
        @NamedQuery(name = "Profile.findbyusername", query = "SELECT p FROM Profile p WHERE p.username = :p_username")
})
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    public String email;

    @Column
    private String location;

    @Column
    private String contactLink;

    @Column
    private String bCryptHash;

    public Long getId() {
        return id;
    }

    public Profile setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Profile setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Profile setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Profile setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getContactLink() {
        return contactLink;
    }

    public Profile setContactLink(String contactLink) {
        this.contactLink = contactLink;
        return this;

    }

    public Profile setPassword(String pass) {
        this.bCryptHash = BCrypt.hashpw(pass, BCrypt.gensalt());
        return this;
    }

    public boolean checkPassword(String pass) {
        return BCrypt.checkpw(pass, this.bCryptHash);
    }
}