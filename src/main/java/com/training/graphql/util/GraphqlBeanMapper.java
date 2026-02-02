package com.training.graphql.util;

import com.training.graphql.datasource.problemz.entity.Problemz;
import com.training.graphql.datasource.problemz.entity.Solutionz;
import com.training.graphql.datasource.problemz.entity.Userz;
import com.training.graphql.datasource.problemz.entity.UserzToken;
import com.training.graphql.generated.types.Problem;
import com.training.graphql.generated.types.ProblemCreateInput;
import com.training.graphql.generated.types.Solution;
import com.training.graphql.generated.types.SolutionCategory;
import com.training.graphql.generated.types.SolutionCreateInput;
import com.training.graphql.generated.types.User;
import com.training.graphql.generated.types.UserAuthToken;
import com.training.graphql.generated.types.UserCreateInput;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Manual mapper between JPA entities and GraphQL generated types.
 *
 * MapStruct is available in the project, but because we have several pieces of
 * custom logic (pretty-time formatting, password hashing, tags join/split,
 * UUID generation), this class currently keeps explicit mapping code for
 * clarity and full control.
 */
public class GraphqlBeanMapper {

    private static final PrettyTime PRETTY_TIME = new PrettyTime();

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(7);

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static User mapToGraphql(Userz original) {
        var result = new User();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);

        result.setAvatar(original.getAvatar());
        result.setCreateDateTime(createDateTime.toLocalDateTime());
        result.setDisplayName(original.getDisplayName());
        result.setEmail(original.getEmail());
        result.setId(original.getId().toString());
        result.setUsername(original.getUsername());

        return result;
    }

    public static Problem mapToGraphql(Problemz original) {
        var result = new Problem();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var convertedSolutions = original.getSolutions().stream()
//                .sorted(Comparator.comparing(Solutionz::getCreationTimestamp).reversed())
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());
        var tagList = List.of(original.getTags().split(","));

        result.setAuthor(author);
        result.setContent(original.getContent());
        result.setCreateDateTime(createDateTime.toLocalDateTime());
        result.setId(original.getId().toString());
        result.setSolutions(convertedSolutions);
        result.setTags(tagList);
        result.setTitle(original.getTitle());
        result.setSolutionCount(convertedSolutions.size());
        result.setPrettyCreateDateTime(PRETTY_TIME.format(Date.from(createDateTime.toInstant())));

        return result;
    }

    public static Solution
    mapToGraphql(Solutionz original) {
        var result = new Solution();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var category = StringUtils.equalsIgnoreCase(
                original.getCategory(), SolutionCategory.EXPLANATION.toString())
                ? SolutionCategory.EXPLANATION
                : SolutionCategory.REFERENCE;

        result.setAuthor(author);
        result.setCategory(category);
        result.setContent(original.getContent());
        result.setCreateDateTime(createDateTime.toLocalDateTime());
        result.setId(original.getId().toString());
        result.setVoteAsBadCount(original.getVoteBadCount());
        result.setVoteAsGoodCount(original.getVoteGoodCount());
        result.setPrettyCreateDateTime(PRETTY_TIME.format(Date.from(createDateTime.toInstant())));

        return result;
    }

    public static UserAuthToken mapToGraphql(UserzToken original) {
        var result = new UserAuthToken();
        var expiryDateTime = original.getExpiryTimestamp().atOffset(ZONE_OFFSET);

        result.setAuthToken(original.getAuthToken());
        result.setExpiryTime(expiryDateTime.toLocalDateTime());

        return result;
    }

    public static Problemz mapToEntity(ProblemCreateInput original, Userz author) {
        var result = new Problemz();

        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setId(UUID.randomUUID());
        result.setSolutions(Collections.emptyList());
        result.setTags(String.join(",", original.getTags()));
        result.setTitle(original.getTitle());

        return result;
    }

    public static Solutionz mapToEntity(SolutionCreateInput original, Userz author, Problemz problemz) {
        var result = new Solutionz();

        result.setCategory(original.getCategory().name());
        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setId(UUID.randomUUID());
        result.setProblemz(problemz);

        return result;
    }

    public static Userz mapToEntity(UserCreateInput original) {
        var result = new Userz();

        result.setId(UUID.randomUUID());
        result.setHashedPassword(PASSWORD_ENCODER.encode(original.getPassword()));
        result.setUsername(original.getUsername());
        result.setEmail(original.getEmail());
        result.setDisplayName(original.getDisplayName());
        result.setAvatar(original.getAvatar());
        result.setActive(true);

        return result;
    }
}

