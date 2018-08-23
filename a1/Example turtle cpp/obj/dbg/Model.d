../obj/dbg/Model.o: Model.cpp Model.h error.h string.h
	g++ -Wall -Werror -Wshadow -pedantic -std=c++11 -I/opt/local/include -I/usr/local/include -I/sw/include -D_THREAD_SAFE -DDARWIN -no-cpp-precomp -g -D_DEBUG -c Model.cpp -o ../obj/dbg/Model.o
