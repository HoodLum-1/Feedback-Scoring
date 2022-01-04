package com.whereismytransport.FeedbackScoring.persistance.repository;

import com.whereismytransport.FeedbackScoring.persistance.entity.ReferenceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceDataRepository extends JpaRepository<ReferenceData, String> {

}

