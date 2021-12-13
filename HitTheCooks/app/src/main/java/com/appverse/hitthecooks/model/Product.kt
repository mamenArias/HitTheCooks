package com.appverse.hitthecooks.model

/**
 * Clase que representa los productos extraídos del web scraping
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param name Nombre del producto
 * @param picUrl url de la imagen del producto
 * @param price Precio del producto
 */
class Product(var name: String ="", var imageUrl : String="", var price : String="") {
}