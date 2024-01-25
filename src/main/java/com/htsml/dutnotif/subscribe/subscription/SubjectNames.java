package com.htsml.dutnotif.subscribe.subscription;

import java.util.regex.Pattern;

public class SubjectNames {
    public static final Pattern groupNamePattern =
            Pattern.compile("^\\d{2}\\.Nh\\d{2}$");

    public static final String GENERAL = "general";

    public static final String ALL_GROUP = "all-group";
}
