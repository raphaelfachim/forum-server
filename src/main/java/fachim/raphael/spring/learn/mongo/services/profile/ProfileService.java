package fachim.raphael.spring.learn.mongo.services.profile;

import fachim.raphael.spring.learn.mongo.domain.Profile;
import fachim.raphael.spring.learn.mongo.domain.User;
import fachim.raphael.spring.learn.mongo.dto.profile.ProfileInput;
import fachim.raphael.spring.learn.mongo.dto.profile.ProfileOutput;
import fachim.raphael.spring.learn.mongo.infra.mapper.ProfileMapper;
import fachim.raphael.spring.learn.mongo.infra.messages.error.ErrorMessage;
import fachim.raphael.spring.learn.mongo.infra.repository.relational.IProfileRespository;
import fachim.raphael.spring.learn.mongo.infra.repository.relational.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private IProfileRespository respository;

    @Autowired
    private IUserRepository userRepository;

    public ProfileOutput create(ProfileInput profileInput){
        Optional<User> _user = userRepository.findById(profileInput.userId());
        if(_user.isEmpty()) throw new RuntimeException(ErrorMessage.ERR3);

        User user = _user.get();
        Profile profile = ProfileMapper.profileInputToProfile(profileInput);
        profile.setUser(user);
        respository.save(profile);

        return ProfileMapper.profileToProfileOutput(profile);
    }

}
