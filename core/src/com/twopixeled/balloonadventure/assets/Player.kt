package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.twopixeled.balloonadventure.assets.assetTypes.Asset
import com.twopixeled.balloonadventure.assets.assetTypes.Touchable
import com.twopixeled.balloonadventure.configs.PIXEL_TO_METER
import com.twopixeled.balloonadventure.configs.SCREEN_HEIGHT
import com.twopixeled.balloonadventure.configs.SCREEN_WIDTH

/**
 * This is the main player in the game
 */
class Player(world: World) : Asset, Touchable {
    private var playerAnimation: Animation<TextureRegion>
    private var playerAtlas: TextureAtlas = TextureAtlas(Gdx.files.internal("player/player.atlas"))
    private var playerBody: Body
    private var animationTime = 0f
    private var balloonCount = 2
    private var isHit = false
    private var hitTimer = 0

    init {
        val playerBodyDef = BodyDef()
        val playerShape = PolygonShape()
        val playerFixtureDef = FixtureDef()

        playerAnimation = Animation(0.1f, playerAtlas.regions)
        playerBodyDef.type = BodyDef.BodyType.DynamicBody
        playerBodyDef.position.set(
                SCREEN_WIDTH / 2 / PIXEL_TO_METER,
                SCREEN_HEIGHT / 1.5f / PIXEL_TO_METER
        )
        playerBodyDef.gravityScale = 0.1f

        playerBody = world.createBody(playerBodyDef)
        playerShape.setAsBox(playerWidth() / 2 / PIXEL_TO_METER, playerHeight() / 2 / PIXEL_TO_METER)

        playerFixtureDef.shape = playerShape
        playerFixtureDef.density = 0.1f
        playerFixtureDef.restitution = 0.5f

        playerBody.createFixture(playerFixtureDef)
        playerBody.userData = this
        playerShape.dispose()
    }

    override fun draw(batch: SpriteBatch) {
        animationTime += Gdx.graphics.deltaTime * 1.5f
        val playerRegion = playerAnimation.getKeyFrame(animationTime, true)

        batch.draw(
                playerRegion,
                (playerBody.position.x * PIXEL_TO_METER) - playerWidth() / 2,
                (playerBody.position.y * PIXEL_TO_METER) - playerHeight() / 2,
                playerWidth() / 2,
                playerHeight() / 2,
                playerWidth(),
                playerHeight(),
                1f,
                1f,
                1f
        )

        countHitTimer()
    }

    override fun dispose() {
        playerAtlas.dispose()
    }

    /**
     * This character detects touches on either left or right half side of the screen. If left
     * half side is touched, player jumps upper left. Otherwise, player jumps upper right
     */
    override fun touchDown(screenX: Float, screenY: Float) {
        // horizontal moving
        if (screenX < (Gdx.graphics.width / 4)) {
            playerBody.applyForceToCenter(-5f, 0f, true)
        }

        if (screenX > Gdx.graphics.width / 4 && screenX < Gdx.graphics.width / 2) {
            playerBody.applyForceToCenter(5f, 0f, true)
        }

        //vertical moving
        val verticalJumpSpd = if (balloonCount > 0) 25f else 0f
        if (screenX > Gdx.graphics.width / 1.5f) {
            playerBody.applyForceToCenter(0f,verticalJumpSpd,true)
        }
    }

    /**
     * Reduce the balloon for the player. If balloon count drops to 0, player can no longer
     * jump higher
     */
    fun popBalloon() {
        if (!isHit) {
            if (balloonCount-- <= 0)
                playerBody.gravityScale = 1f
            else
                playerBody.gravityScale += 0.1f

            isHit = true
        }
    }

    /**
     * Runner width is 10% of screen width
     */
    private fun playerWidth(): Float {
        return SCREEN_WIDTH / 20
    }

    /**
     * Runner height is 5% of screen height
     */
    private fun playerHeight(): Float {
        return SCREEN_HEIGHT / 10
    }

    /**
     * If player is hit, give a short period of immunity to give chance of avoiding bees
     */
    private fun countHitTimer() {
        if (isHit) {
            hitTimer++

            if (hitTimer > 100) {
                isHit = false
                hitTimer = 0
            }
        }
    }
}