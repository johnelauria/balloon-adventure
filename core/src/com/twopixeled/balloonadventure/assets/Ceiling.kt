package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.FixtureDef

/**
 * Creates an invisible ceiling on top of the main game to prevent player from jumping over
 * the screen
 */
class Ceiling(world: World) {

    init {
        val ceilBodyDef = BodyDef()
        val ceilShape = PolygonShape()
        val ceilFixtureDef = FixtureDef()

        ceilBodyDef.type = BodyDef.BodyType.StaticBody
        ceilBodyDef.position.set(0f, Gdx.graphics.height.toFloat())
        val ceilBody = world.createBody(ceilBodyDef)

        ceilShape.setAsBox(Gdx.graphics.width.toFloat(), 1f)
        ceilFixtureDef.shape = ceilShape
        ceilFixtureDef.density = 1f
        ceilBody.createFixture(ceilFixtureDef)

        ceilShape.dispose()
    }
}