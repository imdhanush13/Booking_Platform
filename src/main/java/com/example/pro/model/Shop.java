package com.example.pro.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;

    @ElementCollection
    @CollectionTable(name = "shop_images", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "image_url")
    private List<String> images;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id", nullable = false, unique = true)
    private User provider;
}
