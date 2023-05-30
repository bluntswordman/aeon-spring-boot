package com.finalaeonproject.controller;

import com.finalaeonproject.dto.AuthRequest;
import com.finalaeonproject.dto.AuthResponse;
import com.finalaeonproject.dto.KaryawanRequest;
import com.finalaeonproject.dto.KaryawanResponse;
import com.finalaeonproject.service.KaryawanService;
import com.finalaeonproject.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/karyawan")
@RequiredArgsConstructor
public class KaryawanController {
  private final KaryawanService karyawanService;

  @PostMapping
  public ResponseEntity<Response<KaryawanResponse>> register(@RequestBody @Valid KaryawanRequest karyawanRequest, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Bad Request", null));
    }

    KaryawanResponse karyawan = karyawanService.register(karyawanRequest);
    if (karyawan == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>(false, "Username Already Exists", null));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(true, "Karyawan Created", karyawan));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<KaryawanResponse>> updateKaryawan(@PathVariable("id") Long id, @RequestBody @Valid KaryawanResponse karyawanRequest, Errors errors, HttpServletRequest request) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Bad Request", null));
    }

    Optional<KaryawanResponse> karyawan = karyawanService.findById(id);
    if (karyawan.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Karyawan Not Found", null));
    }

    KaryawanResponse updatedKaryawan = karyawanService.update(id, karyawanRequest, request);
    if (updatedKaryawan == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>(false, "Id you entered is not yours", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Karyawan Updated", updatedKaryawan));
  }

  @GetMapping("/search")
  public ResponseEntity<Response<List<KaryawanResponse>>> searchKaryawan(
      @RequestParam(value = "nama", defaultValue = "", required = false) String nama,
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam(value = "size", defaultValue = "5", required = false) int size
  ) {
    List<KaryawanResponse> karyawan = karyawanService.search(nama, page, size);
    if (karyawan.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Not Found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Karyawan Found", karyawan));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<Optional<KaryawanResponse>>> findById(@PathVariable("id") Long id) {
    Optional<KaryawanResponse> karyawan = karyawanService.findById(id);
    if (karyawan.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(false, "Not Found", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Karyawan Found", karyawan));
  }

  @PostMapping("/login")
  public ResponseEntity<Response<AuthResponse>> login(@RequestBody AuthRequest request) {
    AuthResponse token = karyawanService.login(request);
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, "Login Failed", null));
    }

    return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, "Login Success", token));
  }
}
