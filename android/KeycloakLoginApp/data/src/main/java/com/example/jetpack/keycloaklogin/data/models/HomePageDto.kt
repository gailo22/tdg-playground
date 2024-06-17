package com.example.jetpack.keycloaklogin.data.models

import androidx.room.Entity
import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Shelf
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["id"])
data class HomePageDto(
    val id: Int,
    val name: String,
    val shelves: List<ShelfDto> = arrayListOf()

)


//{
//    "data": {
//    "id": 1,
//    "name": "Home Page",
//    "createdAt": "2024-04-30T07:49:39.419Z",
//    "updatedAt": "2024-04-30T09:18:13.733Z",
//    "publishedAt": "2024-04-30T07:49:42.760Z",
//    "shelves": [
//    {
//        "id": 1,
//        "createdAt": "2024-04-30T07:44:47.286Z",
//        "updatedAt": "2024-04-30T09:19:11.062Z",
//        "publishedAt": "2024-04-30T07:44:54.694Z",
//        "type": "Family",
//        "miniprograms": [
//        {
//            "id": 4,
//            "name": "Chat",
//            "description": "Family Chat"
//        },
//        {
//            "id": 5,
//            "name": "Calendar",
//            "description": "Family Calendar"
//        }
//        ]
//    },
//    {
//        "id": 2,
//        "createdAt": "2024-04-30T08:01:16.443Z",
//        "updatedAt": "2024-04-30T09:19:45.728Z",
//        "publishedAt": "2024-04-30T08:01:19.045Z",
//        "type": "Entertainment",
//        "miniprograms": [
//        {
//            "id": 6,
//            "name": "EPL",
//            "description": "TrueID EPL"
//        },
//        {
//            "id": 7,
//            "name": "Live TV",
//            "description": "TrueID Live TV"
//        }
//        ]
//    }
//    ]
//},
//    "meta": {
//
//}
//}

fun HomePageDto.toDomain(): HomePage {
    val list = shelves.map {
        it.toDomain()
    }.toList()
    return HomePage(name = name, shelves = list)
}