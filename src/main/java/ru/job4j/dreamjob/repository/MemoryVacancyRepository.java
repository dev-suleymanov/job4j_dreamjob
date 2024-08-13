package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {
    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new Vacancy(0, "Intern Java Developer",
                "Help with Java development tasks, suitable for beginners.", now));
        save(new Vacancy(0, "Junior Java Developer",
                "Write simple Java code and perform tests. Some experience required.", now));
        save(new Vacancy(0, "Junior+ Java Developer",
                "Handle small projects, understand databases and web services.", now));
        save(new Vacancy(0, "Middle Java Developer",
                "Develop applications, work with Java frameworks like Spring.", now));
        save(new Vacancy(0, "Middle+ Java Developer",
                "Lead projects, design system architecture, mentor juniors.", now));
        save(new Vacancy(0, "Senior Java Developer",
                "Lead development teams, innovate with advanced Java technologies.", now));
    }
    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            oldVacancy.setTitle(vacancy.getTitle());
            oldVacancy.setDescription(vacancy.getDescription());
            oldVacancy.setCreationDate(vacancy.getCreationDate());
            return oldVacancy;
        }) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}