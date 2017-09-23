package it.discovery.hit.repository;

import it.discovery.hit.domain.Hit;

import java.util.List;

public interface HitRepository {
    List<Hit> findHits(String id);

    void saveHit(Hit hit);
}
