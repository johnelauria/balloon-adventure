package com.twopixeled.balloonadventure.assets.assetTypes

/**
 * Assets of this type react to player's touch on screen
 */
interface Touchable {
    /**
     * Executed once a player touches something in the screen
     */
    fun touchDown(screenX: Float, screenY: Float)
}