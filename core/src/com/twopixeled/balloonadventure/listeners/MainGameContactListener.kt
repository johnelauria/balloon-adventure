package com.twopixeled.balloonadventure.listeners

import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.twopixeled.balloonadventure.assets.BeeLeft
import com.twopixeled.balloonadventure.assets.Player
import com.twopixeled.balloonadventure.stages.MainGame

class MainGameContactListener(private val mainGame: MainGame) : ContactListener {
    override fun endContact(contact: Contact?) {
        val body1 = contact?.fixtureA
        val body2 = contact?.fixtureB

        if (body1?.body?.userData is Player && body2?.body?.userData is BeeLeft) {
            mainGame.popPlayerBalloon()
        }
    }

    override fun beginContact(contact: Contact?) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}