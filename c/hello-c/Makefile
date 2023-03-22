CFLAGS=-std=gnu99 -pedantic -Wall -Wsign-compare -Wwrite-strings -Wtype-limits -Werror
helloProgram: hello.o
	gcc -o hello hello.o 
%.o: %.c
	gcc $(CFLAGS) -c $<
.PHONY: clean
clean:
	rm -f hello *.o *.c~ *.h~