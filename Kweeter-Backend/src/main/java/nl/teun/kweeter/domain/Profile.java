package nl.teun.kweeter.domain;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name = "Profile.all", query = "SELECT p FROM Profile p")
@NamedQuery(name = "Profile.findbyid", query = "SELECT p FROM Profile p WHERE p.id = :p_id")
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactLink() {
        return contactLink;
    }

    public void setContactLink(String contactLink) {
        this.contactLink = contactLink;
    }

    public void setPassword(String pass) {
        this.bCryptHash = BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public boolean checkPassword(String pass) {
        return BCrypt.checkpw(pass, this.bCryptHash);
    }
}