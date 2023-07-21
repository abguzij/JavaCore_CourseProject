package entity.publication;

import entity.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Publication {
    public static final Integer MIN_ID_VALUE = 0;
    private static Integer publicationCounter = 0;
    private Integer id;
    private User author;
    private String theme;
    private String text;
    private List<String> tags;
    private LocalDateTime dateOfPublication;

    public Publication(Integer id, User author, String theme, String text, List<String> tags, LocalDateTime dateOfPublication) {
        this.id = id;
        this.author = author;
        this.theme = theme;
        this.text = text;
        this.tags = tags;
        this.dateOfPublication = dateOfPublication;
    }

    public Publication(User author, String theme, String text, List<String> tags, LocalDateTime dateOfPublication) {
        this.id = publicationCounter;
        this.author = author;
        this.theme = theme;
        this.text = text;
        this.tags = tags;
        this.dateOfPublication = dateOfPublication;
    }

    public Publication(User author, String theme, String text, LocalDateTime dateOfPublication) {
        this.id = publicationCounter;
        this.author = author;
        this.theme = theme;
        this.text = text;
        this.tags = new ArrayList<>();
        this.dateOfPublication = dateOfPublication;
    }
    public Publication() {
        this.id = Publication.MIN_ID_VALUE;
        this.dateOfPublication = LocalDateTime.MIN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(LocalDateTime dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public static void incrementCounter(){
        ++publicationCounter;
    }

    public Publication compareById(Publication publication){
        if(this.id < publication.id ||
                (this.id == 0 && publication.id == 0)){
            return publication;
        }
        return this;
    }

    public Publication compareByDate(Publication publication){
        if(publication.dateOfPublication.isAfter(this.dateOfPublication)){
            return publication;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(
                "[%s] {\n" +
                        "\tАвтор: %s;\n" +
                        "\tСоздано: %s;\n" +
                        "\tТема: %s;\n" +
                        "\tТекст: %s;\n" +
                        "\tТэги: ",
                "Публикация",
                this.author.getUserShortInfo(),
                this.dateOfPublication.toString(),
                this.theme,
                this.text
                )
        );

        if(this.tags == null || this.tags.size() == 0){
            builder.append("Тэги отсутствуют");
        } else {
            tags.stream().forEach(x -> {
                    if(!x.equals("")){
                        builder.append(String.format("#%s, ", x));
                    } else {
                        int indexOfEmptyString = builder.indexOf("");
                        builder.replace(indexOfEmptyString, indexOfEmptyString, "");
                    }
                }
            );
        }

        builder.append(";\n}");

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(id, that.id) && Objects.equals(author, that.author) && Objects.equals(theme, that.theme) && Objects.equals(text, that.text) && Objects.equals(tags, that.tags) && Objects.equals(dateOfPublication, that.dateOfPublication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, theme, text, tags, dateOfPublication);
    }
    public String tagsToString() {
        return this.tags.stream().map(x -> String.format("%s", x)).collect(Collectors.joining(","));
    }
}
