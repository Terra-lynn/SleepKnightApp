import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.Time;
import java.util.Date;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static void initializingDatabase() {
        try(Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "registration_date TEXT NOT NULL, " +
                    "age INTEGER, " +
                    "weight REAL, " +
                    "gender TEXT, " +
                    "bedtime TEXT, " +
                    "sleeptime TEXT, " +
                    "weekly_hours_slept REAL)";
            stmt.execute(sql);

            //new sleep data table
            String sleepData = "CREATE TABLE IF NOT EXISTS sleep_data (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "sleeptime TEXT NOT NULL, " +
                    "wakeuptime TEXT NOT NULL, " +
                    "duration REAL NOT NULL, " +
                    "FOREIGN KEY(username) REFERENCES users(username))";
            stmt.execute(sleepData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email, registration_date) VALUES (?, ?, ?, ?)";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, LocalDate.now().toString());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public static ResultSet getAllUsers() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM users");
    }

    public static UserProfile getUserProfile(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        Map<String, String> profile = new HashMap<>();

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                String username2 = rs.getString("username");
                String userEmail = rs.getString("email");
                int userAge = rs.getInt("age");
                double userWeight = rs.getDouble("weight");
                String userGender = rs.getString("gender");
                String userBedtime = rs.getString("bedtime");
                int userTotalSleep = rs.getInt("weekly_hours_slept");

                return new UserProfile(username, userEmail, userAge, userWeight, userGender, userBedtime, userTotalSleep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateUserProfile(String username, int age, double weight, String gender, String bedtime) {
        String sql = "UPDATE users SET age = ?, weight = ?, gender = ?, bedtime = ? WHERE username = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, age);
            pstmt.setDouble(2, weight);
            pstmt.setString(3, gender);
            pstmt.setString(4, bedtime);
            pstmt.setString(5, username);
            pstmt.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean savesSleepData (String username, Time sleeptime, Time wakeUpTime,double durationHours){
        String sql = "INSERT INTO sleep_data (username, date, sleeptime, wakeuptime, duration) VALUES (?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, LocalDate.now().toString());
            pstmt.setString(3, sleeptime.toString());
            pstmt.setString(4, wakeUpTime.toString());
            pstmt.setDouble(5, durationHours);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //retrieve data for graph
    public static Map<String, Double> getSleepDuration(String username) {
        Map<String, Double> sleepDuration = new LinkedHashMap<>();
        String sql = "SELECT date, duration FROM sleepData WHERE username = ? ORDER BY data ASC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                String duration = rs.getString("duration");
                sleepDuration.put(date, Double.parseDouble(duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sleepDuration;
    }

    public static double getTotalSleepHours(String username) {
        double totalHours = 0.0;
        String query = "SELECT SUM(duration) FROM sleep_data WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalHours = rs.getDouble(1); // SUM() can return NULL, so default is 0.0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalHours;
    }
}
