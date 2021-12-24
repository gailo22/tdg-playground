def get_popular_restaurants(list):
    POPULAR_RESTAURANTS = ['Laut', 'Rosa Mexicano', 'Ootoya', 'Dig Inn']
    
    new_list = []
    for i in list:
        if i in POPULAR_RESTAURANTS:
            new_list.append(i)

    return new_list


print(get_popular_restaurants(['Laut', 'Dig Inn']))