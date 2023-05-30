package com.finalaeonproject.repository;

import com.finalaeonproject.entity.Karyawan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Long> {
  @Query(value = """
      SELECT
        karyawan
      FROM Karyawan karyawan
      WHERE karyawan.nama LIKE :nama
      """)
  Page<Karyawan> findByNamaContaining(@Param("nama") String nama, Pageable pageable);

  @Query(value = """
      SELECT
        karyawan
      FROM Karyawan karyawan
      WHERE karyawan.account.username LIKE :username
      """)
  Optional<Karyawan> findByAccountUsername(@Param("username") String username);
}
