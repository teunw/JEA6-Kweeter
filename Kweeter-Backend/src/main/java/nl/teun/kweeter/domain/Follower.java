package nl.teun.kweeter.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Follower {

    @Id
    @GeneratedValue
    private Long id;

    private Profile profileBeingFollowed;

    @ManyToOne
    private List<Profile> profilesFollowing;

    public Follower() {
        this.profilesFollowing = new ArrayList<>();
    }

    public List<Profile> getProfilesFollowing() {
        return profilesFollowing;
    }

    public void setProfilesFollowing(List<Profile> profilesFollowing) {
        this.profilesFollowing = profilesFollowing;
    }

    public Profile getProfileBeingFollowed() {
        return profileBeingFollowed;
    }

    public void setProfileBeingFollowed(Profile profileBeingFollowed) {
        this.profileBeingFollowed = profileBeingFollowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return Objects.equals(getProfilesFollowing(), follower.getProfilesFollowing()) &&
                Objects.equals(getProfileBeingFollowed(), follower.getProfileBeingFollowed());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getProfilesFollowing(), getProfileBeingFollowed());
    }
}
