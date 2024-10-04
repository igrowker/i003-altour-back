package com.igrowker.altour.persistence.repository;

import com.igrowker.altour.persistence.entity.DestineAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDestineAuditRepository extends JpaRepository<DestineAudit, Long> {
}
