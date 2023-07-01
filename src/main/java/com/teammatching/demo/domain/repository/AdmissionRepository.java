package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
