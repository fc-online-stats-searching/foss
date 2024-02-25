package com.foss.server.dao;

import com.foss.server.domain.match.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchCustomRepository{
    List<String> findNonExistingIds(List<String> idList);
}
