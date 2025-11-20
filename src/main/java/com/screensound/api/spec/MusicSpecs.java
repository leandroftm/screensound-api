package com.screensound.api.spec;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import com.screensound.api.entity.Music;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class MusicSpecs {
    public static Specification<Music> byArtistId(Long artistId) {
        /*root = main table
        cb = CriteriaBuilder
        cb.conjunction() = always true filter - example: artistId == null -> ignore this filter
         **/
        return (root, query, criteriaBuilder) -> {
            if (artistId == null)
                return criteriaBuilder.conjunction();
            //Dynamic JOIN from Music Table -> Artist Table
            Join<Music, Artist> artistJoin = root.join("artist", JoinType.INNER);
            //Generate SQL -> WHERE artist.id = :artistId
            return criteriaBuilder.equal(artistJoin.get("id"), artistId);
        };
    }

    public static Specification<Music> byAlbumId(Long albumId) {
        return (root, query, criteriaBuilder) -> {
            if (albumId == null)
                return criteriaBuilder.conjunction();
            Join<Music, Album> albumJoin = root.join("album", JoinType.INNER);
            return criteriaBuilder.equal(albumJoin.get("id"), albumId);
        };
    }
}
