package com.anyandroid.dagerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

const val TAG = "demo"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var car: Car
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        car.printBrandCar()
        car.startCar()
        car.flashLights()
        car.printCarColor()
        car.printCarYear()
    }
}

class Car @Inject constructor(
    private val engine: Engine,
    private val carBrand: CarBrand,
    @CarColor private val carColor: String,
    @CarYear private val carYear: Int
) {


    fun printBrandCar() {
        Log.d(TAG, carBrand.printBrandCar())
    }

    fun startCar() {
        Log.d(TAG, engine.start())
        Log.d(TAG, "Car started")
    }

    fun flashLights() {
        //println("Lights flashed")
        Log.d(TAG, "Lights flashed")
    }

    fun printCarColor() {
        Log.d(TAG, carColor)
    }

    fun printCarYear() {
        Log.d(TAG, carYear.toString())
    }

}

class Engine @Inject constructor() {

    fun start(): String {
        // println("Engine started")
        //Log.d(TAG, "Engine started")
        return "Engine started"
    }
}

class Bmw @Inject constructor() : CarBrand {
    override fun printBrandCar(): String {
        return "BMW M3"
    }
}

interface CarBrand {
    fun printBrandCar(): String

}

@InstallIn(SingletonComponent::class)
@Module
class ModuleBrand {
    @Singleton
    @Provides
    //provide for hilt the brand of car
    fun provideCarBrand(): CarBrand {
        return Bmw()
    }

    @CarColor
    @Singleton
    @Provides
    fun provideCarColor(): String {
        return "Red"
    }

    @CarYear
    @Singleton
    @Provides
    fun provideCarYear(): Int {
        return 2021
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CarColor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CarYear
