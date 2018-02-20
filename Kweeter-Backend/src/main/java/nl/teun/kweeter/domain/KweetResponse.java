package nl.teun.kweeter.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class KweetResponse extends Kweet {

    @ManyToOne
    public Kweet ParentKweet;

}
