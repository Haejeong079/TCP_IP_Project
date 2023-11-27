package com.example.Pratice.repository;

import com.example.Pratice.entity.DashBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends CrudRepository<DashBoard,Long> {

    @Override
    ArrayList<DashBoard>findAll();

    Optional<DashBoard> findTopByNicknameOrderByUploadDateDesc(String nickname);

    DashBoard findByIdAndNickname(Long id, String nickname);


    DashBoard findByNicknameAndId(String nickname, Long id);
}
