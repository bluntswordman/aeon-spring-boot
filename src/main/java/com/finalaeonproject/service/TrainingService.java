package com.finalaeonproject.service;

import com.finalaeonproject.dto.TrainingDTO;
import com.finalaeonproject.entity.Training;
import com.finalaeonproject.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {
  private final TrainingRepository trainingRepository;
  private final ModelMapper modelMapper;

  public TrainingDTO save(TrainingDTO trainingDTO) {
    Training training = modelMapper.map(trainingDTO, Training.class);
    return modelMapper.map(trainingRepository.save(training), TrainingDTO.class);
  }

  public TrainingDTO update(Long id, TrainingDTO trainingDTO) {
    Training training = modelMapper.map(trainingDTO, Training.class);
    training.setId(id);
    return modelMapper.map(trainingRepository.save(training), TrainingDTO.class);
  }

  public List<TrainingDTO> search(String namaPengajar, int page, int size) {
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    return trainingRepository.findByNamaPengajarContaining(namaPengajar, pageable).map(training -> modelMapper.map(training, TrainingDTO.class)).toList();
  }

  public Optional<TrainingDTO> findById(Long id) {
    return trainingRepository.findById(id).map(training -> modelMapper.map(training, TrainingDTO.class));
  }
}
