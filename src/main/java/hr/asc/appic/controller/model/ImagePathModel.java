package hr.asc.appic.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

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

        if (path == null) {
            paths = Collections.emptyList();
        } else {
            paths = Collections.singletonList(path);
        }
    }
}
