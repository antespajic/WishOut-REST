package hr.asc.appic.persistence.repository;

import hr.asc.appic.persistence.model.Offer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferRepository extends AsyncRepository<Offer> {

    List<Offer> findByWishId(String wishId, Pageable pageable);

    List<Offer> findByWishIdAndUserId(String wishId, String userId);

    List<Offer> findByWishIdAndChosen(String id, boolean chosen);
}
