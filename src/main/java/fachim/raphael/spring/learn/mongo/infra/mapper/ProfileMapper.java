package fachim.raphael.spring.learn.mongo.infra.mapper;

import fachim.raphael.spring.learn.mongo.domain.Profile;
import fachim.raphael.spring.learn.mongo.dto.profile.ProfileInput;
import fachim.raphael.spring.learn.mongo.dto.profile.ProfileOutput;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileMapper {

    public static Profile profileInputToProfile(ProfileInput profileInput){
        return new Profile(
                profileInput.firstName(),
                profileInput.lastName()
        );
    }

    public static ProfileOutput profileToProfileOutput(Profile profile){
        return new ProfileOutput(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName()
        );
    }

    public static List<ProfileOutput> profilesToProfileOutputList(List<Profile> profiles){
        return profiles.stream().map(ProfileMapper::profileToProfileOutput).collect(Collectors.toList());
    }
}
