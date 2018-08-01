package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.twopixeled.balloonadventure.assets.assetTypes.Asset
import com.twopixeled.balloonadventure.assets.assetTypes.Touchable

/**
 * This is the main player in the game
 */
class Player(world: World) : Asset, Touchable {
    private var playerAnimation: Animation<TextureRegion>
    private var playerAtlas: TextureAtlas = TextureAtlas(Gdx.files.internal("player/player.atlas"))
    private var playerBody: Body
    private var animationTime = 0f
    private var leftSpeed = -4f
    private var rightSpeed = 4f
    private var balloonCount = 2
    private var isHit = false
    private var hitTimer = 0

    init {
        val playerBodyDef = BodyDef()
        val playerShape = PolygonShape()
        val playerFixtureDef = FixtureDef()

        playerAnimation = Animation(0.1f, playerAtlas.regions)
        playerBodyDef.type = BodyDef.BodyType.DynamicBody
        playerBodyDef.position.set(Gdx.graphics.width / 4f, Gdx.graphics.height / 2f)
        playerBodyDef.gravityScale = 0.3f

        playerBody = world.createBody(playerBodyDef)
        playerShape.setAsBox(playerWidth() / 2, playerHeight() / 2)

        playerFixtureDef.shape = playerShape
        playerFixtureDef.density = 1f
        playerFixtureDef.restitution = 5f

        playerBody.createFixture(playerFixtureDef)
        playerBody.userData = this
        playerShape.dispose()
    }

    override fun draw(batch: SpriteBatch) {
        animationTime += Gdx.graphics.deltaTime * 1.5f
        val playerRegion = playerAnimation.getKeyFrame(animationTime, true)

        batch.draw(
                playerRegion,
                playerBody.position.x - playerWidth() / 2,
                playerBody.position.y - playerHeight() / 2,
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
        val verticalJumpSpd = if (balloonCount > 0) 4f else 0f
        if (screenX < Gdx.graphics.width / 2) {
            playerBody.setLinearVelocity(
                    Gdx.graphics.width * leftSpeed,
                    Gdx.graphics.height * verticalJumpSpd
            )
            updateHorizontalVelocity(false)
        }

        if (screenX > Gdx.graphics.width / 2) {
            playerBody.setLinearVelocity(
                    Gdx.graphics.width * rightSpeed,
                    Gdx.graphics.height * verticalJumpSpd
            )
            updateHorizontalVelocity(true)
        }
    }

    /**
     * Reduce the balloon for the player. If balloon count drops to 0, player can no longer
     * jump higher
     */
    fun popBalloon() {
        if (!isHit) {
            if (balloonCount-- <= 0)
                playerBody.gravityScale = 20f
            else
                playerBody.gravityScale += 2f

            isHit = true
        }
    }

    /**
     * Adjusts the horizontal velocity of player. Moving the player initially has slow
     * horizontal speed, when moved multiple times to that direction, horizontal speed increases
     * until max speed of 8 is achieved. Once the player is moved to another direction, horizontal
     * speed is reset to 1, and will increase again after simultaneous move to same direction
     */
    private fun updateHorizontalVelocity(isRightTouched: Boolean) {
        val maxHorizontalSpd = if (balloonCount >= 2) 8f else 3f

        if (isRightTouched) {
            leftSpeed = -1f

            if (rightSpeed < maxHorizontalSpd) {
                rightSpeed += 1f
            }
        } else {
            rightSpeed = 1f

            if (leftSpeed > -maxHorizontalSpd) {
                leftSpeed -= 1f
            }
        }
    }

    /**
     * Runner width is 10% of screen width
     */
    private fun playerWidth(): Float {
        return Gdx.graphics.width / 16f
    }

    /**
     * Runner height is 5% of screen height
     */
    private fun playerHeight(): Float {
        return Gdx.graphics.height / 10.5f
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