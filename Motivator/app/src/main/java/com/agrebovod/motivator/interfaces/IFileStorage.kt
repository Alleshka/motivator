package com.agrebovod.motivator.interfaces

interface IFileStorage {
    fun getRandPhase(): String?
    fun save()
}