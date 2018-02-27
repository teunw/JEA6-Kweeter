package nl.teun.kweeter.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet k order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbyid", query = "SELECT k FROM Kweet k WHERE k.id = :k_id order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbyprofile", query = "SELECT k FROM Kweet k WHERE k.author.id = :p_id order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbypublicId", query = "SELECT k FROM Kweet k WHERE k.publicId = :k_publicId order by k.date DESC")
})
public class Kweet implements Serializable {

    @Column(unique = true, nullable = false, updatable = false)
    private String publicId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;

    @Column(nullable = false)
    private String textContent = "";

    @OneToOne
    private Profile author;

    @Column(nullable = false)
    private Date date;

    @OneToMany(fetch = FetchType.LAZY)
    private List<KweetResponse> responses;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Profile> likedBy;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rekweet> rekweets;

    public Kweet() {
        this.date = new Date();
        this.responses = new ArrayList<>();
        this.likedBy = new ArrayList<>();
        this.rekweets = new ArrayList<>();
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId.toString();
    }

    public Long getInternalId() {
        return id;
    }

    public Kweet setInternalId(Long internalId) {
        this.id = internalId;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public Kweet setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public Profile getAuthor() {
        return author;
    }

    public Kweet setAuthor(Profile author) {
        this.author = author;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Kweet setDate(Date date) {
        this.date = date;
        return this;
    }

    public Kweet setDateWithLocalDateTime(LocalDateTime ldate) {
        Instant instant = Instant.from(ldate.atZone(ZoneId.of("GMT")));
        this.date = Date.from(instant);
        return this;
    }

    public Collection<KweetResponse> getResponses() {
        return responses;
    }

    public Kweet setResponses(List<KweetResponse> responses) {
        this.responses = responses;
        return this;
    }

    public Collection<Profile> getLikedBy() {
        return likedBy;
    }

    public Kweet setLikedBy(List<Profile> likedBy) {
        this.likedBy = likedBy;
        return this;
    }

    public Collection<Rekweet> getRekweets() {
        return rekweets;
    }

    public Kweet setRekweets(List<Rekweet> rekweets) {
        this.rekweets = rekweets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kweet kweet = (Kweet) o;
        return Objects.equals(getPublicId(), kweet.getPublicId()) &&
                Objects.equals(id, kweet.id) &&
                Objects.equals(getTextContent(), kweet.getTextContent()) &&
                Objects.equals(getAuthor(), kweet.getAuthor()) &&
                Objects.equals(getDate(), kweet.getDate()) &&
                Objects.equals(getResponses(), kweet.getResponses()) &&
                Objects.equals(getLikedBy(), kweet.getLikedBy()) &&
                Objects.equals(getRekweets(), kweet.getRekweets());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPublicId(), id, getTextContent(), getAuthor(), getDate(), getResponses(), getLikedBy(), getRekweets());
    }
}