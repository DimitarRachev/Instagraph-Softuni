package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.exam.instagraphlite.models.dto.exportDto.PictureExportDto;
import softuni.exam.instagraphlite.models.dto.importDto.PictureImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constant.File.PICTURES_JSON;
import static softuni.exam.instagraphlite.constant.Message.INVALID_PICTURE;
import static softuni.exam.instagraphlite.constant.Message.VALID_PICTURE_FORMAT;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final Validator validator;

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_JSON));
    }

    @Override
    public String importPictures() throws IOException {
        List<String> output = new ArrayList<>();
        PictureImportDto[] pictureImportDtos = gson.fromJson(readFromFileContent(), PictureImportDto[].class);
        for (PictureImportDto dto : pictureImportDtos) {
            Set<ConstraintViolation<PictureImportDto>> violations = validator.validate(dto);
            if (violations.isEmpty() && !pictureRepository.existsByPath(dto.getPath())) {
                pictureRepository.save(mapper.map(dto, Picture.class));
                output.add(String.format(VALID_PICTURE_FORMAT, dto.getSize()));
            }else {
                output.add(INVALID_PICTURE);
            }
        }
        return String.join(System.lineSeparator(), output);
    }

    @Override
    public String exportPictures() {
       return pictureRepository.findAllBySizeGreaterThanOrderBySize(30000.0)
          .stream()
          .map(p -> mapper.map(p, PictureExportDto.class))
          .map(p ->String.format("%.2f - %s", p.getSize(), p.getPath()))
          .collect(Collectors.joining(System.lineSeparator()));
    }
}
