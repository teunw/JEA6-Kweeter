package nl.teun.kweeter.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class KweetResponse extends Kweet {

    @ManyToOne(fetch = FetchType.LAZY)
    public Kweet ParentKweet;

}
