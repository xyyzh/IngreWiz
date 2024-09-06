package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Chef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository; 
import java.util.Optional;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {
    Optional<Chef> findByChefName(String name);
}
