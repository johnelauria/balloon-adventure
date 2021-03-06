package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.twopixeled.balloonadventure.configs.PIXEL_TO_METER

/**
 * Create an invisible wall to prevent player from jumping beyond the left/right playing area
 */
class Walls(world: World) {
    init {
        val wallBodyDef1 = BodyDef()
        val wallBodyDef2 = BodyDef()
        val wallShape = PolygonShape()
        val wallFixtureDef = FixtureDef()

        wallBodyDef1.type = BodyDef.BodyType.StaticBody
        wallBodyDef1.position.set(
                -100 / PIXEL_TO_METER,
                Gdx.graphics.height / PIXEL_TO_METER
        )
        val wallBody1 = world.createBody(wallBodyDef1)

        wallBodyDef2.type = BodyDef.BodyType.StaticBody
        wallBodyDef2.position.set(
                (Gdx.graphics.width + 100) / PIXEL_TO_METER,
                Gdx.graphics.height.toFloat() / PIXEL_TO_METER
        )
        val wallBody2 = world.createBody(wallBodyDef2)

        wallShape.setAsBox(1f, Gdx.graphics.height.toFloat())
        wallFixtureDef.shape = wallShape
        wallFixtureDef.density = 1f
        wallBody1.createFixture(wallFixtureDef)
        wallBody2.createFixture(wallFixtureDef)

        wallShape.dispose()
    }
}