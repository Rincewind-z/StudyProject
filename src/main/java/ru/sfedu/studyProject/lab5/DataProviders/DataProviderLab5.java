package ru.sfedu.studyProject.lab5.DataProviders;

import ru.sfedu.studyProject.lab5.model.Fursuit;
import ru.sfedu.studyProject.lab5.model.Project;

import java.util.Optional;

public interface DataProviderLab5 {
    Optional<Fursuit> getFursuit(long projectId);
}
