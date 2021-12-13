package com.appverse.hitthecooks.interfaces

/**
 * Interfaz que contiene las funciones para transpasar información entre un adapter y su actividad
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
interface RecyclerTransferData {

    /**
     * Función que permite pasar un valor numérico entre una adapter y su actividad
     * @param value Valor (ID) a pasar del adapter a la actividad
     */
    fun passData(value: Int)
}