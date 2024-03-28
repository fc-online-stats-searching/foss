package com.foss.server.dao;

import com.foss.server.domain.match.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MatchCustomRepositoryImpl implements MatchCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<String> findNonExistingIds(List<String> idList) {
        List<Match> existingMatches = mongoTemplate.find(
                Query.query(Criteria.where("_id").in(idList)),
                Match.class
        );

        return idList.stream()
                .filter(id -> existingMatches.stream().noneMatch(match -> match.getId().equals(id)))
                .collect(Collectors.toList());
    }
}
