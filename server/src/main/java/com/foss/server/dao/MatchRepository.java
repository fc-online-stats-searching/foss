package com.foss.server.dao;

import com.foss.server.domain.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String>, MatchCustomRepository {
    @Query("{ 'validation': true, 'matchType': { '$in': ?0 }, $or: [ { 'team1': ?1 }, { 'team2': ?2 } ] }")
    Page<Match> findAllMatches(List<Integer> matchTypes, String ouid1, String ouid2, Pageable pageable);
}

