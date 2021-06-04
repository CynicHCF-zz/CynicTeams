//package com.cynichcf.hcf.team.configuration;
//
//import lombok.Getter;
//import com.cynichcf.hcf.Foxtrot;
//import rip.lazze.libraries.configuration.Configuration;
//import rip.lazze.libraries.configuration.annotations.ConfigData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TeamGeneralConfiguration extends Configuration {
//
//    @ConfigData(path = "teams.disallowed_names")
//    @Getter
//    private static List<String> disallowedNames = new ArrayList<>();
//
//    public TeamGeneralConfiguration() {
//        super(Foxtrot.getInstance(), "config.yml", "./plugins/Teams/");
//        disallowedNames.add("glowstone");
//        disallowedNames.add("miniend");
//        disallowedNames.add("warzone");
//        disallowedNames.add("spawn");
//        disallowedNames.add("end");
//        disallowedNames.add("theend");
//        disallowedNames.add("citadel");
//        load();
//        save();
//    }
//}
