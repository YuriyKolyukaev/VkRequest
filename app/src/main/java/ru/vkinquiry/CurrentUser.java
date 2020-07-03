package ru.vkinquiry;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

public class CurrentUser {
    public static String getAccessToken() {                                                         // возвращает Token если он не равен нулю
        if (VKAccessToken.currentToken() == null) {
            return null;
        }

        return VKAccessToken.currentToken().accessToken;
    }

    public static String getId() {                                                                  // возвращает Id если Token не равен нулю
        if (VKAccessToken.currentToken() == null) {
            return null;
        }

        return VKAccessToken.currentToken().userId;
    }

    public static boolean isAutorized() {                                                           // возвращает false если хотя бы одно из условий не верно:
        return VKSdk.isLoggedIn()                                                                   // если пользователь авторизован
                && VKAccessToken.currentToken() != null                                             // если пользовательский Token не равен нулю
                && !VKAccessToken.currentToken().isExpired();                                       // если пользовательский Token не устарел
    }
}

