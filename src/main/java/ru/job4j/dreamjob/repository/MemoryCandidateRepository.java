package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new Candidate(0, "Ivan Petrov",
                "Newbie in Java, eager to learn and contribute.", now, 1, 0));
        save(new Candidate(0, "Anna Ivanova",
                "Proficient in Java, capable of independent coding and testing.", now, 1, 0));
        save(new Candidate(0, "Pavel Sidorov",
                "Skilled in Java, can handle small to medium projects.", now, 2, 0));
        save(new Candidate(0, "Maria Smirnova",
                "Experienced with Java frameworks like Spring.", now, 3, 0));
        save(new Candidate(0, "Dmitry Popov",
                "Expert in Java development, experienced in leading teams.", now, 1, 0));
        save(new Candidate(0, "Elena Kuznetsova",
                "Seasoned professional, capable of shaping system architecture.", now, 3, 0));
    }
    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> {
            oldCandidate.setName(candidate.getName());
            oldCandidate.setDescription(candidate.getDescription());
            oldCandidate.setCreationDate(candidate.getCreationDate());
            oldCandidate.setCityId(candidate.getCityId());
            oldCandidate.setFileId(candidate.getFileId());
            return oldCandidate;
        }) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
