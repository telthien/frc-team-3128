################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/robotmessagequeue/SerialSender.cpp \
../src/robotmessagequeue/robot_command.cpp \
../src/robotmessagequeue/robot_spi.cpp 

OBJS += \
./src/robotmessagequeue/SerialSender.o \
./src/robotmessagequeue/robot_command.o \
./src/robotmessagequeue/robot_spi.o 

CPP_DEPS += \
./src/robotmessagequeue/SerialSender.d \
./src/robotmessagequeue/robot_command.d \
./src/robotmessagequeue/robot_spi.d 


# Each subdirectory must supply rules for building sources it contributes
src/robotmessagequeue/%.o: ../src/robotmessagequeue/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

