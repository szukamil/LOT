package com.task.lot.repository;

import com.task.lot.model.response.QueryResponse;
import com.task.lot.model.entity.UserVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {
    @Query(value = "SELECT date, Count(*) AS Count FROM (SELECT date  FROM user_visit where IP = :ip) as uvd GROUP BY date"
            , nativeQuery = true)
    Optional<List<QueryResponse>> selectUserVisit(String ip);

    @Query(value = "SELECT DISTINCT IP FROM user_visit", nativeQuery = true)
    Optional<List<String>> selectDistinctIps();
}
