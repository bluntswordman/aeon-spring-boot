package com.finalaeonproject.controller;

import com.finalaeonproject.entity.KaryawanTraining;
import com.finalaeonproject.service.KaryawanService;
import com.finalaeonproject.service.TrainingKaryawanService;
import com.finalaeonproject.service.TrainingService;
import com.finalaeonproject.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/training-karyawan")
@RequiredArgsConstructor
public class TrainingKaryawanController {
  private final TrainingKaryawanService trainingKaryawanService;
  private final TrainingService trainingService;
  private final KaryawanService karyawanService;


  @PostMapping("/{id-karyawan}/{id-training}")
  public ResponseEntity<Response<KaryawanTraining>> createTrainingKaryawan(@PathVariable("id-karyawan") Long idKaryawan, @PathVariable("id-training") Long idTraining) {
    if (trainingService.findById(idTraining).isEmpty() || karyawanService.findById(idKaryawan).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Karyawan or training not found", null));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(true, "Karyawan training created", trainingKaryawanService.save(idKaryawan, idTraining)));
  }

  @GetMapping("/search")
  public ResponseEntity<Response<List<KaryawanTraining>>> searchTrainingKaryawan(
      @RequestParam(value = "nama-karyawan", defaultValue = "", required = false) String namaKaryawan,
      @RequestParam(value = "nama-pengajar", defaultValue = "", required = false) String namaPengajar,
      @RequestParam(value = "tema", defaultValue = "", required = false) String tema,
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam(value = "size", defaultValue = "5", required = false) int size
  ) {
    List<KaryawanTraining> karyawanTrainingList = trainingKaryawanService.search(namaKaryawan, namaPengajar, tema, page, size);
    if (karyawanTrainingList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Karyawan training not found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Karyawan training found", karyawanTrainingList));
  }
}
