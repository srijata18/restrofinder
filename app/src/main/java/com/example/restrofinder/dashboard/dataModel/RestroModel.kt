package com.example.restrofinder.dashboard.dataModel

/*
*
* RestroModel consists of the data class based on the JSON response obtained from url*/
data class RestroModel(val meta : Meta?,
                               val response : Response?)

data class Venues (val id : String?= "",
    val name : String?= "",
    val location : Location?,
    val categories : List<Categories>?,
    val referralId : String?= "",
    val hasPerk : Boolean?= false
)
data class Sw ( val lat : Double? = null,
    val lng : Double?= null
)

data class Response (val venues : List<Venues>?,
    val confident : Boolean?= false,
    val geocode : Geocode?
)

data class Ne (val lat : Double?= null,
    val lng : Double?= null
)
data class Meta (val code : Int?=0,
    val requestId : String?=""
)
data class Location (val address : String?="",
    val crossStreet : String?="",
    val lat : Double?=null,
    val lng : Double?=null,
    val labeledLatLngs : List<LabeledLatLngs>?,
    val postalCode : Int?=0,
    val cc : String?="",
    val city : String?="",
    val state : String?="",
    val country : String?="",
    val formattedAddress : List<String>?
)

data class Icon (val prefix : String?="",
    val suffix : String?=""
)

data class LabeledLatLngs (val label : String?="",
    val lat : Double?=null,
    val lng : Double?=null
)
data class Geometry (val center : Center?,
    val bounds : Bounds?
)

data class Geocode (val what : String?="",
    val where : String?="",
    val feature : Feature?,
    val parents : List<String>?
)

data class Feature (val cc : String?="",
    val name : String?="",
    val displayName : String?="",
    val matchedName : String?="",
    val highlightedName : String?="",
    val woeType : Int?=0,
    val slug : String?="",
    val id : String?="",
    val geometry : Geometry?
)

data class Center (val lat : Double?=null,
    val lng : Double?=null
)

data class Categories (val id : String?="",
    val name : String?="",
    val pluralName : String?="",
    val shortName : String?="",
    val icon : Icon?,
    val primary : Boolean?=false
)
data class Bounds (val ne : Ne?,
    val sw : Sw?
)