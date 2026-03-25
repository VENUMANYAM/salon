package com.salon.repository;

import com.salon.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Owner> findByIdAndActiveTrue(Long id);
}
