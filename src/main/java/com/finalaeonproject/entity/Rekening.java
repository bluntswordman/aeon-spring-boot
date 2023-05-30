package com.finalaeonproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "rekening")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_date is null")
public class Rekening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "jenis")
    private String jenis;

    @Column(name = "nama")
    private String nama;

    @Column(name = "nomor")
    private String nomor;

    @ManyToOne
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;
}
