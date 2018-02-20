package nl.teun.kweeter.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
@NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet k")
@NamedQuery(name = "Kweet.findbyid", query = "SELECT k FROM Kweet k WHERE k.id = :k_id")
@NamedQuery(name = "Kweet.findbyprofile", query = "SELECT k FROM Kweet k WHERE k.author.id = :p_id")
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

    @OneToMany
    private Collection<KweetResponse> responses;

    @ManyToMany
    private Collection<Profile> likedBy;

    @ManyToMany
    private Collection<Rekweet> rekweets;

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

    public Collection<KweetResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<KweetResponse> responses) {
        this.responses = responses;
    }

    public Collection<Profile> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<Profile> likedBy) {
        this.likedBy = likedBy;
    }

    public Collection<Rekweet> getRekweets() {
        return rekweets;
    }

    public void setRekweets(List<Rekweet> rekweets) {
        this.rekweets = rekweets;
    }
}