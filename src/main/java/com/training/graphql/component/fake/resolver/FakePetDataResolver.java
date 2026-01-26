package com.training.graphql.component.fake.resolver;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.datasource.fake.FakePetDataSource;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.Cat;
import com.training.graphql.generated.types.Dog;
import com.training.graphql.generated.types.Pet;
import com.training.graphql.generated.types.PetFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Pets)
    public List<Pet> getPets(@InputArgument(name = "petFilter")
                             Optional<PetFilter> filter) {
        if (filter.isEmpty()) {
            return FakePetDataSource.PET_LIST;
        }

        return FakePetDataSource.PET_LIST.stream().filter(
                pet -> this.matchFilter(filter.get(), pet)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(PetFilter petFilter, Pet pet) {
        if (StringUtils.isBlank(petFilter.getPetType())) {
            return true;
        }

        if (petFilter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())) {
            return pet instanceof Dog;
        } else if (petFilter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())) {
            return pet instanceof Cat;
        } else {
            return false;
        }
    }

}