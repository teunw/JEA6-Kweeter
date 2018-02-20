package nl.teun.kweeter.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class Rekweet extends Kweet {

    @OneToOne
    private Kweet parentKweet;

    public Kweet getParentKweet() {
        return parentKweet;
    }

    public void setParentKweet(Kweet parentKweet) {
        this.parentKweet = parentKweet;
    }
}
