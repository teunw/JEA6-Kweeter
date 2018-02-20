package nl.teun.kweeter.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet k")
@NamedQuery(name = "Kweet.findbyid", query = "SELECT k FROM Kweet k WHERE k.id = :k_id")
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

    @ManyToMany
    private List<Kweet> responses;

    @ManyToMany
    private List<Profile> likedBy;

    @ManyToMany
    private List<Rekweet> rekweets;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId.toString();
    }

    public Long getInternalId() {
        return id;
    }

    public void setInternalId(Long internalId) {
        this.id = internalId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Kweet> getResponses() {
        return responses;
    }

    public void setResponses(List<Kweet> responses) {
        this.responses = responses;
    }

    public List<Profile> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<Profile> likedBy) {
        this.likedBy = likedBy;
    }

    public List<Rekweet> getRekweets() {
        return rekweets;
    }

    public void setRekweets(List<Rekweet> rekweets) {
        this.rekweets = rekweets;
    }
}