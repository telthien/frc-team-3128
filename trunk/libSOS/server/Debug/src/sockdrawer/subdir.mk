################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/sockdrawer/SocketServer.cpp 

C_SRCS += \
../src/sockdrawer/SOSClient.c 

OBJS += \
./src/sockdrawer/SOSClient.o \
./src/sockdrawer/SocketServer.o 

C_DEPS += \
./src/sockdrawer/SOSClient.d 

CPP_DEPS += \
./src/sockdrawer/SocketServer.d 


# Each subdirectory must supply rules for building sources it contributes
src/sockdrawer/%.o: ../src/sockdrawer/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	arm-linux-gnueabi-gcc -I../frc-3128-beagleboard/frc-3128-beagleboard/io-headers -I/home/jamie/libboost/include -O2 -g3 -gstabs -pedantic -Wall -c -fmessage-length=0 -v -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

src/sockdrawer/%.o: ../src/sockdrawer/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cross G++ Compiler'
	arm-linux-gnueabi-g++ -I"/home/jamie/eclipse/workspace/frc-3128-beagleboard/src/qualcomm" -I../frc-3128-beagleboard/frc-3128-beagleboard/io-headers -I"/home/jamie/eclipse/workspace/frc-3128-beagleboard/src/robotmessagequeue" -I"/home/jamie/eclipse/workspace/frc-3128-beagleboard/src" -I/home/jamie/libboost/include -O2 -g3 -gstabs -Wall -c -fmessage-length=0 -std=c++11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

