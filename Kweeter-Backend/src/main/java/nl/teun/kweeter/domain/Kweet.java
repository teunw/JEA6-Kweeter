package nl.teun.kweeter.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Kweet implements Serializable {

    @Column(unique = true, nullable = false, insertable = true, updatable = false)
    private Long publicId = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long internalId = 0L;

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

    public Long getPublicId() {
        return publicId;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }

    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
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