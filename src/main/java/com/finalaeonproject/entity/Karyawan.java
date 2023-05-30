package com.finalaeonproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalaeonproject.constant.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "karyawan")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_date is null")
public class Karyawan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @CreatedDate
  @Column(name = "created_date")
  @JsonIgnore
  private Date createdDate;

  @Column(name = "deleted_date")
  @JsonIgnore
  private Date deletedDate;

  @LastModifiedDate
  @Column(name = "updated_date")
  @JsonIgnore
  private Date updatedDate;

  @Column(name = "alamat")
  private String alamat;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(name = "jenis_kelamin")
  private Gender jenisKelamin;

  @Column(name = "nama")
  private String nama;

  @Column(name = "status", length = 50)
  private String status;

  @OneToOne(orphanRemoval = true)
  @JsonIgnore
  private Account account;

  @OneToOne(mappedBy = "karyawan")
  @JsonIgnore
  private DetailKaryawan detailKaryawan;
}
