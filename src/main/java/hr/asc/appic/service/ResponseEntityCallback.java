package hr.asc.appic.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseEntityCallback<V> implements FutureCallback<V> {

    private DeferredResult<ResponseEntity> response;

    public ResponseEntityCallback(DeferredResult<ResponseEntity> response) {
        this.response = response;
    }

    @Override
    public void onSuccess(V v) {
        response.setResult(v == null ? ResponseEntity.ok().build() : ResponseEntity.ok(v));
    }

    @Override
    public void onFailure(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        response.setResult(ResponseEntity.status(500).build());
    }
}

