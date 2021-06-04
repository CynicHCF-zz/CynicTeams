package com.cynichcf.hcf.scoreboard;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.scoreboard.ScoreboardConfiguration;
import rip.lazze.libraries.scoreboard.TitleGetter;

public class FoxtrotScoreboardConfiguration {

    public static ScoreboardConfiguration create() {
        ScoreboardConfiguration configuration = new ScoreboardConfiguration();

        configuration.setTitleGetter(new TitleGetter(HCF.getInstance().getMapHandler().getScoreboardTitle()));
        configuration.setScoreGetter(new FoxtrotScoreGetter());

        return (configuration);
    }

}