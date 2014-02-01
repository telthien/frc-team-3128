/*
 * robot_command.h
 *
 *  Created on: Nov 1, 2013
 *      Author: jamie
 */

#include "robot_command.h"
#include <vector>
#include <boost/foreach.hpp>
#include "../libSOS/SOSProtocol.h"
#include <sstream>

std::string string_to_hex(const std::string& input)
{
    static const char* const lut = "0123456789ABCDEF";
    size_t len = input.length();

    std::string output;
    output.reserve(2 * len);
    for (size_t i = 0; i < len; ++i)
    {
        const unsigned char c = input[i];
        output.push_back(lut[c >> 4]);
        output.push_back(lut[c & 15]);
    }
    return output;
}

robot_command::robot_command(in_port_t return_id, unsigned char opcode)
:_return_id(return_id),
_opcode(opcode),
 _data(),
 _extraString()
{

}

robot_command::robot_command(in_port_t return_id, unsigned char opcode, boost::optional<std::vector<signed short> > data)
:_return_id(return_id),
_opcode(opcode),
 _data(data),
 _extraString()
{

}

robot_command::robot_command(in_port_t return_id, unsigned char opcode, boost::optional<std::vector<signed short> > data, boost::optional<std::string> extraString)
:_return_id(return_id),
_opcode(opcode),
 _data(data),
 _extraString(extraString)
{

}

boost::optional<robot_command> robot_command::factory(std::vector<char> bytes)
{
	std::vector<char>::const_iterator 		currentBytePtr(bytes.begin());
	in_port_t								return_id;
	char 									opcode;
	boost::optional<std::vector<short> > 	shorts;
	boost::optional<std::string> 			string;


	return_id = parse_return_id(currentBytePtr);

	++currentBytePtr;
	opcode = parse_opcode(currentBytePtr);

	// Move into shorts if any.
	++currentBytePtr;
	// should NOW equal END_TRANSMISSION or START_SHORT_TRANSMISSION

	std::cout << "command: " << string_to_hex(std::string(bytes.begin(), bytes.end())) << std::endl;

	//returns an empty optional if there isn't a short
	shorts = parse_shorts(currentBytePtr);

	string = parse_string(currentBytePtr);

	return boost::optional<robot_command>(robot_command(return_id, opcode, shorts, string));

}

in_port_t robot_command::parse_return_id(std::vector<char>::const_iterator & currentBytePtr)
{
	if(*currentBytePtr != START_TRANSMISSION)
	{
		// Bail and return empty value.
		std::cerr << "RobotCommand: the header of the command is incorrect\nShould be:" << START_TRANSMISSION << " Was: " << *currentBytePtr << std::endl;
		return 0x0;
	}

	// Have start of command.  Begin parsing.


	++currentBytePtr;
	//should now be the first byte of the ASCII-encoded id

	std::vector<char> IDStorage;
	while(*(++currentBytePtr) != END_ID)
	{
		IDStorage.push_back(*currentBytePtr);
	}

	//should now equl END_ID
	return static_cast<in_port_t>(atoi(IDStorage.data()));
}

char robot_command::parse_opcode(std::vector<char>::const_iterator & currentBytePtr)
{
	// Get opcode.
	++currentBytePtr;
	char opcode = *currentBytePtr;
	++currentBytePtr;

	if(*currentBytePtr != END_OPCODE)
	{
		// Bail and return empty value.
		std::cerr << "RobotCommand: the end of the opcode command is incorrect\nShould be:" << END_OPCODE << " Was: " << *currentBytePtr << std::endl;
		return 0x0;
	}
	return opcode;
}

boost::optional<std::vector<signed short> > robot_command::parse_shorts(std::vector<char>::const_iterator & currentBytePtr)
{
	if(*currentBytePtr == START_SHORT_TRANSMISSION)
	{
		//give the optional type a value
		std::vector<short> shortVector;
		boost::optional<std::vector<short> > shorts(shortVector);

		 // Loop until we don't recieve the start of a short
		while(*currentBytePtr == START_SHORT_TRANSMISSION)
		{
			std::vector<char> shortBytesStorage;
			while(*(++currentBytePtr) != END_SHORT)
			{
				shortBytesStorage.push_back(*currentBytePtr);
			}
			shorts.get().push_back(static_cast<short>(atoi(shortBytesStorage.data())));
			++currentBytePtr;
		}

		return shorts;
	}
	else
	{
		//create an empty optional
		boost::optional<std::vector<signed short> > shorts;
		return shorts;
	}

}

boost::optional<std::string> robot_command::parse_string(std::vector<char>::const_iterator & currentBytePtr)
{
	if(*currentBytePtr == START_STRING_TRANSMISSION)
	{
		std::stringstream stringstream;
		while(*(++currentBytePtr) != END_STRING)
		{

			stringstream << *currentBytePtr;
		}

		boost::optional<std::string> string(std::string(stringstream.str()));
		return string;
	}
	else
	{
		boost::optional<std::string> string;
		return string;
	}

}


std::ostream & operator<<(std::ostream & leftOp, const robot_command rightOp)
{
	leftOp << "robot_command dump: --------------------------------------" << std::endl;

	leftOp << "Return ID: " << std::dec << rightOp._return_id << std::endl;

	leftOp << "Opcode: 0x" << std::hex << ((int)rightOp._opcode) << std::endl;
	if(rightOp._data.is_initialized())
	{
		leftOp << "Data: ";
		for(short subdata : rightOp._data.get())
		{
			leftOp << std::dec << (int)(subdata) << " ";
		}
		leftOp << std::endl;
	}
	if(rightOp._extraString.is_initialized())
	{
		leftOp << "Extra String: \"" << rightOp._extraString.get() << "\"" << std::endl;
	}
	leftOp << std::endl;

	return leftOp;

}
