package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new Candidate(0, "Ivan Petrov",
                "Newbie in Java, eager to learn and contribute.", now));
        save(new Candidate(0, "Anna Ivanova",
                "Proficient in Java, capable of independent coding and testing.", now));
        save(new Candidate(0, "Pavel Sidorov",
                "Skilled in Java, can handle small to medium projects.", now));
        save(new Candidate(0, "Maria Smirnova",
                "Experienced with Java frameworks like Spring.", now));
        save(new Candidate(0, "Dmitry Popov",
                "Expert in Java development, experienced in leading teams.", now));
        save(new Candidate(0, "Elena Kuznetsova",
                "Seasoned professional, capable of shaping system architecture.", now));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> {
            oldCandidate.setName(candidate.getName());
            oldCandidate.setDescription(candidate.getDescription());
            oldCandidate.setCreationDate(candidate.getCreationDate());
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
