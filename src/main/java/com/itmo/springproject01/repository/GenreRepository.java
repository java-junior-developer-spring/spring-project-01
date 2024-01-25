package com.itmo.springproject01.repository;

import com.itmo.springproject01.entity.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
// PagingAndSortingRepository;
// JpaRepository: CrudRepository + PagingAndSortingRepository;


// inArchive -> in_archive
public interface GenreRepository extends CrudRepository<Genre, String> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_genres WHERE in_archive = false")
    List<Genre> findActive();

    @Query("SELECT g FROM Genre g WHERE g.inArchive = true")
    List<Genre> findNonActive();

    List<Genre> findAllByInArchiveFalse();

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_genres WHERE name = :name")
    // Genre byName(String name);
    Genre byName(@Param("name") String genreName);

    Genre findByName(String name);
}
// GenreController -> GenreService -> GenreRepository repository