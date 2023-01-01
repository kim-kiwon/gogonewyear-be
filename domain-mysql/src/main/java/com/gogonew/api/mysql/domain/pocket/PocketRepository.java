package com.gogonew.api.mysql.domain.pocket;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<Pocket, UUID> {

}
