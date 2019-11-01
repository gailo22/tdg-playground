def sort_restaurants():
    restaurants = [
        {
            "name": "Ootoya",
            "rating": 8.0
        },
        {
            "name": "Chipotle",
            "rating": 6.6
        },
        {
            "name": "Burger & Lobster",
            "rating": 7.2
        },
        {
            "name": "Laut",
            "rating": 8.1
        }
    ]

    return sorted(restaurants, key=lambda kv: kv["rating"] * -1)


print(sort_restaurants())
