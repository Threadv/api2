package com.ifenghui.storybookapi.app.app.response;

/**
 * Created by wang
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Help;

public class HelpResponse extends ApiPageResponse {
    Help help;

    public Help getHelp() {
        return help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }
}
