package hr.asc.appic.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import hr.asc.appic.persistence.model.Offer;

public interface OfferRepository extends AsyncRepository<Offer> {

    List<Offer> findByWishId(String wishId, Pageable pageable);

    List<Offer> findByWishIdAndUserId(String wishId, String userId);

    List<Offer> findByWishIdAndChosen(String id, boolean chosen);
}
