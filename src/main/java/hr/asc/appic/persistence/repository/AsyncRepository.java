package hr.asc.appic.persistence.repository;

import java.math.BigInteger;

import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

public interface AsyncRepository<T> extends Repository<T, BigInteger> {

	@Async
	ListenableFuture<T> findById(BigInteger id);
	
	@Async
	ListenableFuture<T> save(T t);
	
	@Async
	ListenableFuture<Void> delete(BigInteger id);
}
