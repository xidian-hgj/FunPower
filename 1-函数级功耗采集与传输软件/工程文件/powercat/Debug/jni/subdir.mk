################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/cpuinfo.c \
../jni/infosend.c \
../jni/netinfo.c \
../jni/parse_params.c \
../jni/powercat.c \
../jni/ring_buffer.c 

OBJS += \
./jni/cpuinfo.o \
./jni/infosend.o \
./jni/netinfo.o \
./jni/parse_params.o \
./jni/powercat.o \
./jni/ring_buffer.o 

C_DEPS += \
./jni/cpuinfo.d \
./jni/infosend.d \
./jni/netinfo.d \
./jni/parse_params.d \
./jni/powercat.d \
./jni/ring_buffer.d 


# Each subdirectory must supply rules for building sources it contributes
jni/%.o: ../jni/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


