package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.twopixeled.balloonadventure.assets.assetTypes.Asset

/**
 * Displays left and right arrows to show where the user can tap to make the player fly left or
 * right
 */
class Arrow : Asset {
    private val arrowTexture = Texture(Gdx.files.internal("arrow.png"))
    private val leftArrowSprite = Sprite(arrowTexture)
    private val rightArrowSprite = Sprite(arrowTexture)

    init {
        leftArrowSprite.setSize(Gdx.graphics.width / 8f, Gdx.graphics.height / 4f)
        leftArrowSprite.rotate(180f)
        leftArrowSprite.setPosition(
                (Gdx.graphics.width / 10) - (leftArrowSprite.width / 2),
                (Gdx.graphics.height / 4.5f) - (leftArrowSprite.height / 2)
        )
        leftArrowSprite.setAlpha(0.5f)

        rightArrowSprite.setSize(Gdx.graphics.width / 8f, Gdx.graphics.height / 4f)
        rightArrowSprite.setPosition(
                (Gdx.graphics.width / 2.5f) - (rightArrowSprite.width / 2),
                (Gdx.graphics.height / 4.5f) - (rightArrowSprite.height / 2)
        )
        rightArrowSprite.setAlpha(0.5f)
    }

    override fun draw(batch: SpriteBatch) {
        leftArrowSprite.draw(batch)
        rightArrowSprite.draw(batch)
    }

    override fun dispose() {
        arrowTexture.dispose()
    }
}