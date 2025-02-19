package com.example.backend.repositories;

import com.example.backend.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // Các phương thức truy vấn bổ sung nếu cần

}
