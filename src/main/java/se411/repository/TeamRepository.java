package se411.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se411.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    // CrudRepository provides:
    // - save(Team) for POST operations
    // - findById(String) for GET by id
    // - findAll() for GET all
}

