package hr.asc.appic.persistence.repository;

import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

public interface AsyncRepository<T> extends Repository<T, String> {

    @Async
    ListenableFuture<T> findById(String id);

    @Async
    ListenableFuture<T> save(T t);

    @Async
    ListenableFuture<Void> delete(String id);
}
