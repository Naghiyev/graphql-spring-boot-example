package com.training.graphql;

import com.training.graphql.datasource.problemz.entity.Problemz;
import com.training.graphql.datasource.problemz.entity.Solutionz;
import com.training.graphql.datasource.problemz.entity.Userz;
import com.training.graphql.datasource.problemz.entity.UserzToken;
import com.training.graphql.repository.ProblemzRepository;
import com.training.graphql.repository.SolutionzRepository;
import com.training.graphql.repository.UserzRepository;
import com.training.graphql.repository.UserzTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
				"org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
class GraphqlApplicationTests {

	@TestConfiguration
	static class TestRepositoriesConfig {

		@Bean
		ProblemzRepository problemzRepository() {
			return new InMemoryProblemzRepository();
		}

		@Bean
		SolutionzRepository solutionzRepository() {
			return new InMemorySolutionzRepository();
		}

		@Bean
		UserzRepository userzRepository() {
			return new InMemoryUserzRepository();
		}

		@Bean
		UserzTokenRepository userzTokenRepository() {
			return new InMemoryUserzTokenRepository();
		}
	}

	/**
	 * Simple no-op in-memory implementation of {@link ProblemzRepository} for tests.
	 * It is only used to satisfy Spring's dependency requirements when loading the context.
	 */
	static class InMemoryProblemzRepository implements ProblemzRepository {

		@Override
		public List<Problemz> findAllByOrderByCreationTimestampDesc() {
			return Collections.emptyList();
		}

		@Override
		public List<Problemz> findByKeyword(String keyword) {
			return Collections.emptyList();
		}

		@Override
		public <S extends Problemz> S save(S entity) {
			return entity;
		}

		@Override
		public <S extends Problemz> Iterable<S> saveAll(Iterable<S> entities) {
			return entities;
		}

		@Override
		public Optional<Problemz> findById(UUID uuid) {
			return Optional.empty();
		}

		@Override
		public boolean existsById(UUID uuid) {
			return false;
		}

		@Override
		public Iterable<Problemz> findAll() {
			return Collections.emptyList();
		}

		@Override
		public Iterable<Problemz> findAllById(Iterable<UUID> uuids) {
			return Collections.emptyList();
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(UUID uuid) {
			// no-op
		}

		@Override
		public void delete(Problemz entity) {
			// no-op
		}

		@Override
		public void deleteAllById(Iterable<? extends UUID> uuids) {
			// no-op
		}

		@Override
		public void deleteAll(Iterable<? extends Problemz> entities) {
			// no-op
		}

		@Override
		public void deleteAll() {
			// no-op
		}
	}

	static class InMemorySolutionzRepository implements SolutionzRepository {

		@Override
		public List<Solutionz> findByKeyword(String keyword) {
			return Collections.emptyList();
		}

		@Override
		public void addVoteBadCount(UUID id) {
			// no-op
		}

		@Override
		public void addVoteGoodCount(UUID id) {
			// no-op
		}

		@Override
		public <S extends Solutionz> S save(S entity) {
			return entity;
		}

		@Override
		public <S extends Solutionz> Iterable<S> saveAll(Iterable<S> entities) {
			return entities;
		}

		@Override
		public Optional<Solutionz> findById(UUID uuid) {
			return Optional.empty();
		}

		@Override
		public boolean existsById(UUID uuid) {
			return false;
		}

		@Override
		public Iterable<Solutionz> findAll() {
			return Collections.emptyList();
		}

		@Override
		public Iterable<Solutionz> findAllById(Iterable<UUID> uuids) {
			return Collections.emptyList();
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(UUID uuid) {
			// no-op
		}

		@Override
		public void delete(Solutionz entity) {
			// no-op
		}

		@Override
		public void deleteAllById(Iterable<? extends UUID> uuids) {
			// no-op
		}

		@Override
		public void deleteAll(Iterable<? extends Solutionz> entities) {
			// no-op
		}

		@Override
		public void deleteAll() {
			// no-op
		}
	}

	static class InMemoryUserzRepository implements UserzRepository {

		@Override
		public Optional<Userz> findByUsernameIgnoreCase(String username) {
			return Optional.empty();
		}

		@Override
		public Optional<Userz> findUserByToken(String authToken) {
			return Optional.empty();
		}

		@Override
		public void activateUser(String username, boolean isActive) {
			// no-op
		}

		@Override
		public <S extends Userz> S save(S entity) {
			return entity;
		}

		@Override
		public <S extends Userz> Iterable<S> saveAll(Iterable<S> entities) {
			return entities;
		}

		@Override
		public Optional<Userz> findById(UUID uuid) {
			return Optional.empty();
		}

		@Override
		public boolean existsById(UUID uuid) {
			return false;
		}

		@Override
		public Iterable<Userz> findAll() {
			return Collections.emptyList();
		}

		@Override
		public Iterable<Userz> findAllById(Iterable<UUID> uuids) {
			return Collections.emptyList();
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(UUID uuid) {
			// no-op
		}

		@Override
		public void delete(Userz entity) {
			// no-op
		}

		@Override
		public void deleteAllById(Iterable<? extends UUID> uuids) {
			// no-op
		}

		@Override
		public void deleteAll(Iterable<? extends Userz> entities) {
			// no-op
		}

		@Override
		public void deleteAll() {
			// no-op
		}
	}

	static class InMemoryUserzTokenRepository implements UserzTokenRepository {

		@Override
		public <S extends UserzToken> S save(S entity) {
			return entity;
		}

		@Override
		public <S extends UserzToken> Iterable<S> saveAll(Iterable<S> entities) {
			return entities;
		}

		@Override
		public Optional<UserzToken> findById(UUID uuid) {
			return Optional.empty();
		}

		@Override
		public boolean existsById(UUID uuid) {
			return false;
		}

		@Override
		public Iterable<UserzToken> findAll() {
			return Collections.emptyList();
		}

		@Override
		public Iterable<UserzToken> findAllById(Iterable<UUID> uuids) {
			return Collections.emptyList();
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(UUID uuid) {
			// no-op
		}

		@Override
		public void delete(UserzToken entity) {
			// no-op
		}

		@Override
		public void deleteAllById(Iterable<? extends UUID> uuids) {
			// no-op
		}

		@Override
		public void deleteAll(Iterable<? extends UserzToken> entities) {
			// no-op
		}

		@Override
		public void deleteAll() {
			// no-op
		}
	}

	@Test
	void contextLoads() {
	}

}
