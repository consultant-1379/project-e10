package com.backend.repository;
import com.backend.entities.DataNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DataNodeRepository extends JpaRepository<DataNode, Integer> {

}
