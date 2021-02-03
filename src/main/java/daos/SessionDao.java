package daos;

import entities.Session;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDao implements CRUD<Session> {

    private final Connection connection;
    private static final String INSERT_SESSION =
            "INSERT INTO sessions (film_id, start_at, week_day) VALUES (?, ?, ?)";
    private static final String READ_ALL_SESSIONS = "SELECT * FROM sessions";
    private static final String GET_BY_ID = "SELECT * FROM sessions WHERE id = ?";

    public SessionDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    @Override
    public Session create(Session session) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SESSION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, session.getFilm().getId());
            preparedStatement.setTime(2, session.getStartAt());
            preparedStatement.setObject(3, session.getWeekDay());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            session.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }


    @Override
    public List<Session> readAll() {
        List<Session> sessionList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SESSIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                sessionList.add(of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionList;
    }

    public static Session of(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int filmId = resultSet.getInt("film_id");
            Time startAt = resultSet.getTime("start_at");
            String weekDay = resultSet.getString("week_day");

            return new Session.Builder()
                    .withId(id)
                    .withFilm(new FilmDao().getById(filmId))
                    .withTimeStartAt(startAt)
                    .withWeekDay(weekDay)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Error");
        }
    }

    public Session getSessionById(int sessionId){
        Session session = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, sessionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            session = of(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }
}
