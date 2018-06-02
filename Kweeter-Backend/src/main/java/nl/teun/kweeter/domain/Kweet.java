package nl.teun.kweeter.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet k order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbyid", query = "SELECT k FROM Kweet k WHERE k.id = :k_id order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbyprofile", query = "SELECT k FROM Kweet k WHERE k.author.id = :p_id order by k.date DESC"),
        @NamedQuery(name = "Kweet.findbypublicId", query = "SELECT k FROM Kweet k WHERE k.publicId = :k_publicId order by k.date DESC"),
        @NamedQuery(name = "Kweet.findafter", query = "SELECT k FROM Kweet k WHERE k.date > :k_date order by k.date DESC")
})
@Indexed
public class Kweet implements Serializable, Comparable<Kweet> {


    @Column(unique = true, nullable = false, updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;

    @Column(nullable = false, length = 160)
    @Field
    private String textContent = "";

    @OneToOne(cascade = CascadeType.REFRESH)
    private Profile author;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Profile> likedBy = emptyList();

    public Kweet() {
    }

    public String getPublicId() {
        return publicId;
    }

    public Kweet setPublicId(UUID publicId) {
        this.publicId = publicId.toString();
        return this;
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

    public static List<Kweet> deserializeKweetListJson(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<Kweet>>() {
        }.getType());
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Kweet setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public List<Profile> getLikedBy() {
        return likedBy;
    }

    public Kweet setLikedBy(List<Profile> likedBy) {
        this.likedBy = likedBy;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kweet kweet = (Kweet) o;
        return Objects.equals(getPublicId(), kweet.getPublicId()) &&
                Objects.equals(id, kweet.id) &&
                Objects.equals(getTextContent(), kweet.getTextContent()) &&
                Objects.equals(getAuthor(), kweet.getAuthor()) &&
                Objects.equals(getDate(), kweet.getDate()) &&
                Objects.equals(getLikedBy(), kweet.getLikedBy());
    }

    @Override
    public String toString() {
        return "Kweet{" +
                "publicId='" + publicId + '\'' +
                ", id=" + id +
                ", textContent='" + textContent + '\'' +
                ", author=" + author +
                ", date=" + date +
                ", likedBy=" + likedBy +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublicId(), id, getTextContent(), getAuthor(), getDate(), getLikedBy());
    }

    @Override
    public int compareTo(@NotNull Kweet o) {
        return this.date.compareTo(o.date);
    }

    public void setId(Long id) {
        this.id = id;
    }
}