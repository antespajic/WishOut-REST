package hr.asc.appic.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import hr.asc.appic.persistence.model.Offer;

public interface OfferRepository extends AsyncRepository<Offer> {

    List<Offer> findByWishId(String id, Pageable pageable);
}
