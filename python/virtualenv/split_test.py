def user_in_experiment(user_id):
    if int(user_id) % 2 != 0:
        return True
    return False


print(user_in_experiment(100))
print(user_in_experiment(101))