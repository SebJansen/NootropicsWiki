package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils;

import android.content.Context;

public class CoverPhotoHelper {

    public static int getRequestCodeFor(String key, Context context) {

        return context.getSharedPreferences("RequestCodes", 0).getInt(key, 0);
    }
}
