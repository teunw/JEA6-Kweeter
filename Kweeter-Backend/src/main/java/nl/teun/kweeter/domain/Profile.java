package nl.teun.kweeter.domain;

import nl.teun.kweeter.authentication.ProfileRole;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "Profile.all", query = "SELECT p FROM Profile p ORDER BY p.id DESC"),
        @NamedQuery(name = "Profile.findbyid", query = "SELECT p FROM Profile p WHERE p.id = :p_id ORDER BY p.id DESC"),
        @NamedQuery(name = "Profile.findbyemail", query = "SELECT p FROM Profile p WHERE p.email = :p_email ORDER BY p.id"),
        @NamedQuery(name = "Profile.findbyusername", query = "SELECT p FROM Profile p WHERE p.username = :p_username ORDER BY p.id")
})
public class Profile implements Serializable, Cloneable {

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

    @Column
    private String bio;

    @Column
    private ProfileRole role;

    public Profile() {
    }

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

    public String getBio() {
        return bio;
    }

    public Profile setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public boolean checkPassword(String pass) {
        return BCrypt.checkpw(pass, this.bCryptHash);
    }

    public ProfileRole getRole() {
        return role;
    }

    public Profile setRole(ProfileRole role) {
        this.role = role;
        return this;
    }

    public boolean hasRole(String role) {
        return this.role.name().equals(role);
    }

    @Override
    public Profile clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        if (clone instanceof Profile) {
            return (Profile) clone;
        }
        throw new CloneNotSupportedException("Clone failed, clone not a profile");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(getId(), profile.getId()) &&
                Objects.equals(getUsername(), profile.getUsername()) &&
                Objects.equals(getDisplayName(), profile.getDisplayName()) &&
                Objects.equals(getEmail(), profile.getEmail()) &&
                Objects.equals(getLocation(), profile.getLocation()) &&
                Objects.equals(getContactLink(), profile.getContactLink()) &&
                Objects.equals(bCryptHash, profile.bCryptHash) &&
                Objects.equals(getBio(), profile.getBio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getDisplayName(), getEmail(), getLocation(), getContactLink(), bCryptHash, getBio());
    }
}