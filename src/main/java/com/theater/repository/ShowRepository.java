package com.theater.repository;


import com.theater.entities.Show;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @EntityGraph(attributePaths = {"seats","theatre","movie"})
    @Query("SELECT ms FROM movie_show ms WHERE ms.id = :id")
    Show getShowById(@Param("id") Long id);
}
