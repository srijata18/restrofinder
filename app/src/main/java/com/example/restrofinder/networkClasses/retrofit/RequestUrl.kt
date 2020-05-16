package com.example.restrofinder.networkClasses.retrofit

class RequestUrl {

    companion object {
        const val BASE_URL = "https://api.foursquare.com/"
        //add valid client id and secret id for proper construction of the url
        const val CLIENT_ID = ""
        const val CLIENT_SECRET = ""
        const val VERSION = "20200123"
        const val DATA_URL = "v2/venues/search?categoryId=%s&near=Los+Angeles&limit=50&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&v=${VERSION}"
    }
}