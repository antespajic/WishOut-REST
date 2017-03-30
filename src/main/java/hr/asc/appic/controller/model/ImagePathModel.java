package hr.asc.appic.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ImagePathModel {

    private BigInteger id;
    private List<String> paths;

    public ImagePathModel(BigInteger id, String path) {
        this.id = id;
        this.paths = Collections.singletonList(path);
    }
}
