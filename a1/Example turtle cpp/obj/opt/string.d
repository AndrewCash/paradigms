../obj/opt/string.o: string.cpp string.h error.h
	g++ -Wall -Werror -Wshadow -pedantic -std=c++11 -I/opt/local/include -I/usr/local/include -I/sw/include -D_THREAD_SAFE -DDARWIN -no-cpp-precomp -O3 -c string.cpp -o ../obj/opt/string.o
