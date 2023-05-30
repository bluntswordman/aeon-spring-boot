package com.finalaeonproject.repository;

import com.finalaeonproject.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
  @Query(value = """
      SELECT
        training
      FROM Training training
      WHERE training.namaPengajar LIKE :namaPengajar
      """)
  Page<Training> findByNamaPengajarContaining(@Param("namaPengajar") String namaPengajar, Pageable pageable);
}
