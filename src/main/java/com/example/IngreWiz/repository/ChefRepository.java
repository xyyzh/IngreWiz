package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Chef;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ChefRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChefRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Chef> findByChefName(String name) {
        String sql = "SELECT * FROM chef WHERE name = ?";
        List<Chef> chefs = jdbcTemplate.query(sql, ps -> ps.setString(1, name), new ChefRowMapper());
        return chefs.isEmpty() ? Optional.empty() : Optional.of(chefs.get(0));
    }

    public Optional<Chef> findById(Long id) {
        String sql = "SELECT * FROM chef WHERE id = ?";
        List<Chef> chefs = jdbcTemplate.query(sql, ps -> ps.setLong(1, id), new ChefRowMapper());
        return chefs.isEmpty() ? Optional.empty() : Optional.of(chefs.get(0));
    }   

    public Optional<Chef> findByEmail(String email) {
        String sql = "SELECT * FROM chef WHERE email = ?";
        List<Chef> chefs = jdbcTemplate.query(sql, ps -> ps.setString(1, email), new ChefRowMapper());
        return chefs.isEmpty() ? Optional.empty() : Optional.of(chefs.get(0));
    }

    public Chef addChef(Chef chef) {
        if (findByEmail(chef.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Chef with the same email already exists");
        }
        String sql = "INSERT INTO chef (name, email, category) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, chef.getChefName(), chef.getEmail(), chef.getPreferredCuisineCategory().name());
        return chef;
    }

    public void updateChef(Chef chef) {
        if (!findById(chef.getId()).isPresent()) {
            throw new IllegalArgumentException("Chef does not exist");
        }
        String sql = "UPDATE chef SET phoneNumber = ?, profilePictureUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, chef.getPhoneNumber(), chef.getProfilePictureUrl(), chef.getId());
    }

    public Iterable<Chef> findAll() {
        String sql = "SELECT * FROM chef";
        return jdbcTemplate.query(sql, new ChefRowMapper());
    }

    //map rows of a ResultSet to instances of the Chef class.
    private static class ChefRowMapper implements RowMapper<Chef> {
        @Override
        public Chef mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Chef chef = new Chef();
            chef.setId(rs.getLong("id"));
            // Map other fields as necessary
            return chef;
        }
    }
}
