package com.racheal.fundadmin.fundadmin.repository;


import com.racheal.fundadmin.fundadmin.models.Fund;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FundRepository extends JpaRepository<Fund, UUID> {

    @EntityGraph(attributePaths = "transactions")
    Optional<Fund> findByFundName(String fundName);
}
