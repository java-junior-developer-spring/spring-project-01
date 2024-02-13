package com.itmo.springproject01.info.queries;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoxRepository extends JpaRepository<Box, Integer>, JpaSpecificationExecutor<Box> {
    List<Box> findAllByHeight(int height);

    @Query("SELECT b.width FROM Box b WHERE b.height = :height")
    List<Integer> widthListByHeightJPQL(int height);

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM tb_boxes WHERE height = :height")
    int numberOFBoxesByHeightSQL(int height);

    List<Box> findAllByHeightLessThanAndWidthLessThanAndLengthLessThan(int maxHeight, int maxWidth, int maxLength);

    @Query(nativeQuery = true, value = "SELECT SUM(height + width + length) FROM tb_boxes WHERE id = :id")
    int sumSizeByIdSQL(int id);


    @Transactional // import jakarta.transaction.Transactional;
    @Modifying // @Transactional(readOnly = false)
    @Query("UPDATE  Box b SET b.width = :newWidth WHERE b.height = :height")
    int updateWidthByHeight(int height, int newWidth);

}
