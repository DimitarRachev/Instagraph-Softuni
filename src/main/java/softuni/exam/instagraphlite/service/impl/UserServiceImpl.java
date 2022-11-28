package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.exam.instagraphlite.models.dto.exportDto.UserWithPostsExportDto;
import softuni.exam.instagraphlite.models.dto.importDto.UserImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constant.File.USERS_JSON;
import static softuni.exam.instagraphlite.constant.Message.INVALID_USER;
import static softuni.exam.instagraphlite.constant.Message.VALID_USER_FORMAT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final Validator validator;

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_JSON));
    }

    @Override
    public String importUsers() throws IOException {
        List<String> output = new ArrayList<>();
        for (UserImportDto dto : gson.fromJson(readFromFileContent(), UserImportDto[].class)) {
            Set<ConstraintViolation<UserImportDto>> constraintViolations = validator.validate(dto);
            Optional<Picture> optionalPicture = pictureRepository.findByPath(dto.getProfilePicture());
            if (constraintViolations.isEmpty() && optionalPicture.isPresent() && !userRepository.existsByUsername(dto.getUsername())) {
                User user = mapper.map(dto, User.class);
                user.setPicture(optionalPicture.get());
                userRepository.save(user);
                output.add(String.format(VALID_USER_FORMAT, user.getUsername()));
            } else {
                output.add(INVALID_USER);
            }
        }


        return String.join(System.lineSeparator(), output);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return
          userRepository
            .getAllOrderByPostsSizeAndId()
            .stream()
            .map(u -> mapper.map(u, UserWithPostsExportDto.class))
            .map(UserWithPostsExportDto::toString)
            .collect(Collectors.joining(System.lineSeparator()));
    }
}
