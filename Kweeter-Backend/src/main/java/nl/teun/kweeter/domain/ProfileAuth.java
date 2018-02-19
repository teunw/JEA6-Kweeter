package nl.teun.kweeter.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "ProfileAuth.token", query = "SELECT pa FROM ProfileAuth pa WHERE pa.token = :token"),
        @NamedQuery(name = "ProfileAuth.id", query = "SELECT pa FROM ProfileAuth pa WHERE pa.id = :id")
})
public class ProfileAuth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Profile profile;

    @Column
    private UUID token;

    public ProfileAuth() {
    }

    public void generateNewToken() {
        this.token = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
