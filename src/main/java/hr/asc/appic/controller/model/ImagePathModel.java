package hr.asc.appic.controller.model;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ImagePathModel {

    private String id;
    private List<String> paths;

    public ImagePathModel(String id, String path) {
        this.id = id;
        this.paths = Collections.singletonList(path);
    }
}
