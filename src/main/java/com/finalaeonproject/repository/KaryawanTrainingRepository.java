package com.finalaeonproject.repository;

import com.finalaeonproject.entity.KaryawanTraining;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanTrainingRepository extends JpaRepository<KaryawanTraining, Long> {
  @Query(value = """
      SELECT
        karyawanTraining
      FROM KaryawanTraining karyawanTraining
      WHERE karyawanTraining.karyawan.nama LIKE :namaKaryawan
        AND karyawanTraining.training.namaPengajar LIKE :namaPengajar
        AND karyawanTraining.training.tema LIKE :temaTraining
      """)
  Page<KaryawanTraining> findByNamaKaryawanAndNamaPengajarAndTemaTraining(@Param("namaKaryawan") String namaKaryawan, @Param("namaPengajar") String namaPengajar, @Param("temaTraining") String temaTraining, Pageable pageable);
}
