package hr.asc.appic.persistence.repository;

import hr.asc.appic.persistence.model.Offer;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

public interface OfferRepository extends AsyncRepository<Offer> {

    List<Offer> findByWishId(BigInteger id, Pageable pageable);
}
