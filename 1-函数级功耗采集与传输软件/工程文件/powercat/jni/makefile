CC = gcc
CFLAGS = -I /opt/android-ndk-r9/platforms/android-18/arch-arm/usr/include
OBJ = powercat.o ring_buffer.o cpuinfo.o infosend.o parse_params.o
 
powercat: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)


