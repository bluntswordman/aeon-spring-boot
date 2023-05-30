package com.finalaeonproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detail_karyawan")
public class DetailKaryawan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "id")
  private long id;

  @Column(name = "nik", nullable = false, unique = true, length = 16)
  private String nik;

  @Column(name = "npwp", nullable = false, unique = true, length = 16)
  private String npwp;

  @JsonIgnore
  @OneToOne()
  @JoinColumn(name = "karyawan_id")
  private Karyawan karyawan;
}
