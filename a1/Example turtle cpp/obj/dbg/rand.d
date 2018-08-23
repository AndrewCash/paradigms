../obj/dbg/rand.o: rand.cpp rand.h error.h
	g++ -Wall -Werror -Wshadow -pedantic -std=c++11 -I/opt/local/include -I/usr/local/include -I/sw/include -D_THREAD_SAFE -DDARWIN -no-cpp-precomp -g -D_DEBUG -c rand.cpp -o ../obj/dbg/rand.o
