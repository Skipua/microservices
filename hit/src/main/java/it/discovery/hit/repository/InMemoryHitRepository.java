package it.discovery.hit.repository;

import it.discovery.hit.domain.Hit;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Repository
public class InMemoryHitRepository implements HitRepository{
    private final MultiValueMap<String, Hit> hits = new LinkedMultiValueMap<>();

    @Override
    public List<Hit> findHits(String id) {
        return hits.get(id);
    }

    @Override
    public void saveHit(Hit hit) {
        hits.add(hit.getObjectId(), hit);
    }

    @Override
    public int findHitCount(String id) {
        if(!hits.containsKey(id)) {
            return 0;
        }
        return hits.get(id).size();
    }
}
