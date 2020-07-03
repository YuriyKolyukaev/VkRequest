package ru.vkinquiry

import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKSdk

class CurrentUserNew {
    
    companion object {
        
        fun getAccessToken(): String? = VKAccessToken.currentToken()?.accessToken
        
        fun getId(): String? = VKAccessToken.currentToken()?.userId
        
        fun isAuthorized(): Boolean? = 
                VKSdk.isLoggedIn() 
                        && VKAccessToken.currentToken() != null
                        && !VKAccessToken.currentToken().isExpired
    }
}