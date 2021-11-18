package ru.hzerr;

import ru.hzerr.config.profile.Profile;

import java.util.Locale;

public class CastTest {

    public static void main(String[] args) {
        System.out.println(Locale.getDefault().getCountry());
    }

    public static Profile getProfile() {
        return getProfile0(null);
    }

    public static <T> T getProfile0(T profile) {
        return profile;
    }
}
