package com.example.backend.repositories;

import com.example.backend.models.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer> {
    // Các phương thức truy vấn bổ sung nếu cần
    SocialAccount findByProviderAndProviderId(String provider, String providerId);
}
