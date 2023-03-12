package com.zennyel.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zennyel.player.Character;
import com.zennyel.player.Skill;
import com.zennyel.player.SkillType;
import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String table;

    private Connection connection;

    public MySQL(FileConfiguration config) {
        this.host = config.getString("MySQL.host");
        this.port = config.getInt("MySQL.port");
        this.database = config.getString("MySQL.database");
        this.username = config.getString("MySQL.username");
        this.password = config.getString("MySQL.password");
        this.table = config.getString("MySQL.table");
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            String sqlPlayer = "CREATE TABLE IF NOT EXISTS " + table + "(" +
                    "uuid VARCHAR(36) NOT NULL," +
                    "level DOUBLE NOT NULL," +
                    "agility INT NOT NULL," +
                    "dexterity INT NOT NULL," +
                    "strength INT NOT NULL," +
                    "health INT NOT NULL," +
                    "points INT NOT NULL," +
                    "PRIMARY KEY (uuid)" +
                    ");";

            String sqlSkill = "CREATE TABLE IF NOT EXISTS skills (" +
                    "id INT AUTO_INCREMENT," +
                    "uuid VARCHAR(36) NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "level INT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (uuid) REFERENCES " + table + "(uuid)" +
                    ");";

            stmt.executeUpdate(sqlPlayer);
            stmt.executeUpdate(sqlSkill);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertPlayer(Character player, UUID playerId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM " + table + " WHERE uuid=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, playerId.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se o jogador já existe, atualiza as informações
                sql = "UPDATE " + table + " SET level=?, agility=?, dexterity=?, strength=?, health=?, points=? WHERE uuid=?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, player.getLevel());
                stmt.setInt(2, player.getAgility());
                stmt.setInt(3, player.getDexterity());
                stmt.setInt(4, player.getStrength());
                stmt.setInt(5, player.getHealth());
                stmt.setInt(6, player.getPoints());
                stmt.setString(7, playerId.toString());
                stmt.executeUpdate();
            } else {
                // Se o jogador não existe, cria um novo registro
                sql = "INSERT INTO " + table + "(uuid, level, agility, dexterity, strength, health, points) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, playerId.toString());
                stmt.setDouble(2, player.getLevel());
                stmt.setInt(3, player.getAgility());
                stmt.setInt(4, player.getDexterity());
                stmt.setInt(5, player.getStrength());
                stmt.setInt(6, player.getHealth());
                stmt.setInt(7, player.getPoints());
                stmt.executeUpdate();
            }

            for (Skill skill : player.getSkills()) {
                insertSkill(playerId, skill);
            }
        } catch (SQLException e) {
            // Lida com a exceção aqui (pode ser registrando um log, exibindo uma mensagem de erro, etc)
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertSkill(UUID playerId, Skill skill) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO skills (id, name, level) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, playerId.toString());
            stmt.setString(2, skill.getName());
            stmt.setInt(3, skill.getLevel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Character getPlayer(UUID playerId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM " + table  + " WHERE uuid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, playerId.toString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                double level = rs.getDouble("level");
                int agility = rs.getInt("agility");
                int dexterity = rs.getInt("dexterity");
                int strength = rs.getInt("strength");
                int health = rs.getInt("health");
                int points = rs.getInt("points");
                List<Skill> skills = getSkills(playerId);
                Character player = new Character(level, agility, dexterity, strength, health, skills);
                player.setPoints(points);
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Skill> getSkills(UUID playerId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Skill> skills = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT * FROM skills WHERE UUID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, playerId.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int level = rs.getInt("level");
                Skill skill = new Skill(level, null);
                setSkillType(skill);
                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return skills;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setSkillType(Skill skill){
        String name = skill.getName();
        switch (name){
            case "fast_attack":
                skill.setType(SkillType.FAST_ATTACK);
            case "heavy_attack":
                skill.setType(SkillType.HEAVY_ATTACK);
            case "light_attack":
                skill.setType(SkillType.LIGHT_ATTACK);
                break;
        }
    }

}