package com.finalaeonproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalaeonproject.constant.Gender;
import com.finalaeonproject.entity.Account;
import com.finalaeonproject.entity.DetailKaryawan;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KaryawanRequest {
  private String alamat;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Gender jenisKelamin;

  private String nama;

  private String status;

  private DetailKaryawan detailKaryawan;

  private Account account;
}
