#include<stdio.h>

int return_me(int);

int main() {

    int x = 10;
    printf("x: %d\n", x);

    x = return_me(x);
    printf("x: %d\n", x);

    return 0;
}

int return_me(int num) {
    return num;
}
