package com.finalaeonproject.service;

import com.finalaeonproject.entity.Karyawan;
import com.finalaeonproject.entity.KaryawanTraining;
import com.finalaeonproject.entity.Training;
import com.finalaeonproject.repository.KaryawanRepository;
import com.finalaeonproject.repository.KaryawanTrainingRepository;
import com.finalaeonproject.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingKaryawanService {
  private final KaryawanTrainingRepository karyawanTrainingRepository;
  private final KaryawanRepository karyawanRepository;
  private final TrainingRepository trainingRepository;
  private final ModelMapper modelMapper;

  public KaryawanTraining save(Long idKaryawan, Long idTraining) {
    Karyawan karyawan = modelMapper.map(karyawanRepository.findById(idKaryawan).orElseThrow(), Karyawan.class);
    Training training = modelMapper.map(trainingRepository.findById(idTraining).orElseThrow(), Training.class);

    KaryawanTraining karyawanTraining = new KaryawanTraining();
    karyawanTraining.setKaryawan(karyawan);
    karyawanTraining.setTraining(training);

    return karyawanTrainingRepository.save(karyawanTraining);
  }

  public List<KaryawanTraining> search(String namaKaryawan, String namaPengajar, String temaTraining, int page, int size) {
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    return karyawanTrainingRepository.findByNamaKaryawanAndNamaPengajarAndTemaTraining(namaKaryawan, namaPengajar, temaTraining, pageable).getContent();
  }
}
