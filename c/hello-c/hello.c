#include <stdio.h>
#include <stdlib.h>

#include "myheader.h"

int main(void) {
    printf("Hello World\n");
    printf("me: %d\n", return_me(10));
    return EXIT_SUCCESS;
}


int return_me(int i) {
    return i;
}

