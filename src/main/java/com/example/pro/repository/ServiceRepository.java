package com.example.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pro.model.ShopServices;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ShopServices, Long> {
    List<ShopServices> findByShopId(Long shopId);
}
