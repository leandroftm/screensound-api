package com.screensound.api.spec;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class AlbumSpecs {
    public static Specification<Album> byArtistId(Long artistId) {
        return ((root, query, criteriaBuilder) -> {
            if (artistId == null)
                return criteriaBuilder.conjunction();
            Join<Album, Artist> artistJoin = root.join("artist", JoinType.INNER);
            return criteriaBuilder.equal(artistJoin.get("id"), artistId);
        });
    }
}
