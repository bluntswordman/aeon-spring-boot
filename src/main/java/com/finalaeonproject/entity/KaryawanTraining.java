package com.finalaeonproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "karyawan_training")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_date is null")
public class KaryawanTraining {
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

  @ManyToOne
  @JoinColumn(name = "karyawan_id")
  private Karyawan karyawan;

  @ManyToOne
  @JoinColumn(name = "training_id")
  private Training training;
}
