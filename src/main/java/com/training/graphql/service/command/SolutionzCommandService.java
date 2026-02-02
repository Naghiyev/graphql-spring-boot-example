package com.training.graphql.service.command;


import com.training.graphql.datasource.problemz.entity.Solutionz;
import com.training.graphql.repository.SolutionzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

@Service
public class SolutionzCommandService {

    @Autowired
    private SolutionzRepository repository;

    private Sinks.Many<Solutionz> solutionzSink = Sinks.many().multicast().onBackpressureBuffer();

    public Solutionz createSolutionz(Solutionz s) {
        return  repository.save(s);
    }

    public Optional<Solutionz> voteBad(UUID solutionId) {
        repository.addVoteBadCount(solutionId);
        var updated = repository.findById(solutionId);

        if (updated.isPresent()) {
            solutionzSink.tryEmitNext(updated.get());
        }

        return updated;
    }

    public Optional<Solutionz> voteGood(UUID solutionId) {
        repository.addVoteGoodCount(solutionId);
        var updated = repository.findById(solutionId);

        if (updated.isPresent()) {
            solutionzSink.tryEmitNext(updated.get());
        }

        return updated;
    }

    public Flux<Solutionz> solutionzFlux() {
        return solutionzSink.asFlux();
    }

}
