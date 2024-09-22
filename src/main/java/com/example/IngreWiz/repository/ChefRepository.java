package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        String sql = "INSERT INTO chef (name, email, category) VALUES (?, ?, ?)";
        
        // allows to retrieve auto-generated keys immediately after the insert operation.
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, chef.getChefName());
            ps.setString(2, chef.getEmail());
            ps.setString(3, chef.getPreferredCuisineCategory().name());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            chef.setId(keyHolder.getKey().longValue());
            System.out.println("Generated ID: " + keyHolder.getKey().longValue()); // Debug statement
        } else {
            System.out.println("KeyHolder is null"); // Debug statement
        }
        return chef;
    }

    public void updateChef(Chef chef) {
        if (!findById(chef.getId()).isPresent()) {
            throw new IllegalArgumentException("Chef does not exist");
        }
        String sql = "UPDATE chef SET email = ?, category = ?, phone_number = ?, bio = ? WHERE id = ?";
        jdbcTemplate.update(sql, chef.getEmail(), chef.getPreferredCuisineCategory().name(), chef.getPhoneNumber(), chef.getBio(), chef.getId());
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
            chef.setChefName(rs.getString("name"));
            chef.setEmail(rs.getString("email"));
            chef.setPreferredCuisineCategory(Category.valueOf(rs.getString("category")));
            chef.setPhoneNumber(rs.getString("phone_number"));
            chef.setBio(rs.getString("bio"));
            return chef;
        }
    }
}
