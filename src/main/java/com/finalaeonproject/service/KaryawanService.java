package com.finalaeonproject.service;

import com.finalaeonproject.dto.AuthRequest;
import com.finalaeonproject.dto.AuthResponse;
import com.finalaeonproject.dto.KaryawanRequest;
import com.finalaeonproject.dto.KaryawanResponse;
import com.finalaeonproject.entity.DetailKaryawan;
import com.finalaeonproject.entity.Karyawan;
import com.finalaeonproject.repository.AccountRepository;
import com.finalaeonproject.repository.DetailKaryawanRepository;
import com.finalaeonproject.repository.KaryawanRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KaryawanService {
  private final KaryawanRepository karyawanRepository;
  private final DetailKaryawanRepository detailKaryawanRepository;
  private final AccountRepository accountRepository;

  private final ModelMapper modelMapper;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public KaryawanResponse register(KaryawanRequest karyawanRequest) {
    Karyawan karyawan = modelMapper.map(karyawanRequest, Karyawan.class);

    if (karyawanRepository.findByAccountUsername(karyawan.getAccount().getUsername()).isPresent()) {
      return null;
    }

    karyawan.getAccount().setPassword(passwordEncoder.encode(karyawan.getAccount().getPassword()));

    karyawan.getDetailKaryawan().setKaryawan(karyawan);
    karyawan.setDetailKaryawan(detailKaryawanRepository.save(karyawan.getDetailKaryawan()));
    karyawan.setAccount(accountRepository.save(karyawan.getAccount()));

    return modelMapper.map(karyawanRepository.save(karyawan), KaryawanResponse.class);
  }

  public KaryawanResponse update(Long id, KaryawanResponse karyawanRequest, HttpServletRequest request) {
    Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
    if (karyawan == null) {
      return null;
    }

    if (karyawan.getId() != getKaryawanProfile(request).getId()) {
      return null;
    }

    DetailKaryawan detailKaryawan = detailKaryawanRepository.findById(karyawan.getDetailKaryawan().getId()).orElse(null);
    if (detailKaryawan == null) {
      return null;
    }

    karyawan.setAlamat(karyawanRequest.getAlamat());
    karyawan.setDateOfBirth(karyawanRequest.getDateOfBirth());
    karyawan.setJenisKelamin(karyawanRequest.getJenisKelamin());
    karyawan.setNama(karyawanRequest.getNama());
    karyawan.setStatus(karyawanRequest.getStatus());

    detailKaryawan.setNik(karyawanRequest.getDetailKaryawan().getNik());
    detailKaryawan.setNpwp(karyawanRequest.getDetailKaryawan().getNpwp());

    karyawanRepository.save(karyawan);
    detailKaryawanRepository.save(detailKaryawan);

    return modelMapper.map(karyawan, KaryawanResponse.class);
  }

  public List<KaryawanResponse> search(String nama, int page, int size) {
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    return karyawanRepository.findByNamaContaining(nama, pageable).map(karyawan -> modelMapper.map(karyawan, KaryawanResponse.class)).toList();
  }

  public Optional<KaryawanResponse> findById(Long id) {
    return karyawanRepository.findById(id).map(karyawan -> modelMapper.map(karyawan, KaryawanResponse.class));
  }

  public AuthResponse login(AuthRequest request) {
    if (accountRepository.findByUsername(request.getUsername()).isEmpty()) {
      return null;
    }

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    var token = jwtService.generateToken(accountRepository.findByUsername(request.getUsername()).get());
    if (token == null) {
      return null;
    }

    return AuthResponse.builder().token(token).build();
  }

  public Karyawan getKaryawanProfile(HttpServletRequest request) {
    String token = extractTokenFromRequest(request);
    if (token != null && jwtService.isTokenValid(token)) {
      String username = jwtService.extractUsername(token);
      return karyawanRepository.findByAccountUsername(username).orElse(null);
    }

    return null;
  }

  private String extractTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }

    return null;
  }
}
