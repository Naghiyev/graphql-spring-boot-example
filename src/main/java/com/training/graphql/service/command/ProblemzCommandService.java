package com.training.graphql.service.command;


import com.training.graphql.datasource.problemz.entity.Problemz;
import com.training.graphql.repository.ProblemzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProblemzCommandService {

    private Sinks.Many<Problemz> problemzSink = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    private ProblemzRepository repository;

    public Problemz createProblemz(Problemz p) {
        var created = repository.save(p);

        problemzSink.tryEmitNext(created);

        return created;
    }

    public Flux<Problemz> problemzFlux() {
        return problemzSink.asFlux();
    }

}
