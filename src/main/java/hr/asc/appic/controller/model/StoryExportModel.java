package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class StoryExportModel {

    private StoryModel story;
    private UserLightViewModel creator;
    private UserLightViewModel sponsor;
    private InteractionModel interaction;
}
