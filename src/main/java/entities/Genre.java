package entities;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {
    private static final Logger LOG = LoggerFactory.getLogger(Genre.class);

    private int id;
    private String genreName;
    private String genreNameUK;

    public Genre(String genreName, String genreNameUK) {
        this.genreName = genreName;
        this.genreNameUK = genreNameUK;
    }

    public Genre(int id, String genreName, String genreNameUK) {
        this.id = id;
        this.genreName = genreName;
        this.genreNameUK = genreNameUK;
    }

    public static Genre of(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String genreName = resultSet.getString("genre_name");
            String genreNameUK = resultSet.getString("genre_name_uk");
            return new Genre(id, genreName, genreNameUK);
        } catch (SQLException e) {
            LOG.error("SQLException in of method of Genre class", e);
        }
        //todo
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreNameUK() {
        return genreNameUK;
    }

    public void setGenreNameUK(String genreNameUK) {
        this.genreNameUK = genreNameUK;
    }

    @Override
    public String toString() {
        return genreName;
    }
}
