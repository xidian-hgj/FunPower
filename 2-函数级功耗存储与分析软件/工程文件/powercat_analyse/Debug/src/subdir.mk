################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/data_analyse.c \
../src/parse_params.c \
../src/powercat_analyse.c 

OBJS += \
./src/data_analyse.o \
./src/parse_params.o \
./src/powercat_analyse.o 

C_DEPS += \
./src/data_analyse.d \
./src/parse_params.d \
./src/powercat_analyse.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


