package com.finalaeonproject.controller;

import com.finalaeonproject.dto.TrainingDTO;
import com.finalaeonproject.service.TrainingService;
import com.finalaeonproject.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
@Validated
public class TrainingController {
  private final TrainingService trainingService;

  @PostMapping
  public ResponseEntity<Response<TrainingDTO>> insert(@Validated @RequestBody TrainingDTO trainingDTO, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Bad Request", null));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(true, "Training Created", trainingService.save(trainingDTO)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<TrainingDTO>> update(@PathVariable Long id,@Validated @RequestBody TrainingDTO trainingDTO, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Bad Request", null));
    }

    Optional<TrainingDTO> training = trainingService.findById(id);
    if (training.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Training Not Found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Training Updated", trainingService.update(id, trainingDTO)));
  }

  @GetMapping("/search")
  public ResponseEntity<Response<List<TrainingDTO>>> search(
      @RequestParam(value = "namaPengajar", defaultValue = "", required = false) String namaPengajar,
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam(value = "size", defaultValue = "5", required = false) int size
  ) {
    List<TrainingDTO> trainingDTOList = trainingService.search(namaPengajar, page, size);
    if (trainingDTOList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Not Found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Training Found", trainingDTOList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<Optional<TrainingDTO>>> findById(@PathVariable("id") Long id) {
    Optional<TrainingDTO> trainingDTO = trainingService.findById(id);
    if (trainingDTO.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Training Not Found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Training Found", trainingDTO));
  }
}
