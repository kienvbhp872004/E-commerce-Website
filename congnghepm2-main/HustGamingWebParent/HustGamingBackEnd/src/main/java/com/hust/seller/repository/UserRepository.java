package com.hust.seller.repository;
import com.hust.seller.entity.Role;
import com.hust.seller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Integer> {
        // Custom query to find User by Email

        Optional<User> findByEmail(String email);
        Optional<User> findByUsername(String username);
        Optional<User> findByToken(String token);
        List<User> findByRoles_RoleName(String roleName);
        Optional<User> findByUserID(int id);
        @Modifying
        @Query("UPDATE User u SET u.image = :images WHERE u.userID = :id")
        void updateImageByUserID(@Param("id") int id, @Param("images") String images);
        @Query("UPDATE User u SET u.full_name = :fullName, u.phone_number = :phoneNumber, u.address = :address WHERE u.userID = :id")
        void updateInformationByUserID(@Param("fullName") String fullName,
                                       @Param("phoneNumber") String phoneNumber,
                                       @Param("address") String address,
                                       @Param("id") int id);

    }

