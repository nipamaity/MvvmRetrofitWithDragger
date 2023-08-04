package com.nipa.mvvmretrofitwithdragger.model

data class Feeds( var albumId      : Int,
                  var id           : Int,
                  var title        : String,
                  var url          : String,
                  var thumbnailUrl : String,
                    var liked :Boolean = false)
