package com.foss.server.dao;

import com.foss.server.domain.match.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {

}

