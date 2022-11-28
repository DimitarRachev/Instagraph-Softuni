package softuni.exam.instagraphlite.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.importDto.PostImportDto;
import softuni.exam.instagraphlite.models.dto.importDto.PostImportWrapperDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.instagraphlite.constant.File.POSTS_XML;
import static softuni.exam.instagraphlite.constant.Message.INVALID_POST;
import static softuni.exam.instagraphlite.constant.Message.VALID_POST_FORMAT;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_XML));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        List<String> output = new ArrayList<>();
        JAXBContext context = JAXBContext.newInstance(PostImportWrapperDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PostImportWrapperDto wrapperDto = (PostImportWrapperDto) unmarshaller.unmarshal(new FileReader(POSTS_XML));
        for (PostImportDto dtoPost : wrapperDto.getPosts()) {
            Set<ConstraintViolation<PostImportDto>> constraintViolations = validator.validate(dtoPost);
            Optional<User> optionalUser = userRepository.findByUsername(dtoPost.getUser().getUsername());
            Optional<Picture> optionalPicture = pictureRepository.findByPath(dtoPost.getPicture().getPath());
            if (constraintViolations.isEmpty() && optionalPicture.isPresent() && optionalUser.isPresent()) {
                Post post = mapper.map(dtoPost, Post.class);
                post.setUser(optionalUser.get());
                post.setPicture(optionalPicture.get());
                postRepository.save(post);
                output.add(String.format(VALID_POST_FORMAT, dtoPost.getUser().getUsername()));
            } else {
                output.add(INVALID_POST);
            }
        }
        return String.join(System.lineSeparator(), output);
    }
}
