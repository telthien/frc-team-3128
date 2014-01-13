/*
 * LogHolder.h
 *
 *  Created on: Jan 1, 2014
 *      Author: jamie
 */

#ifndef LOGHOLDER_H_
#define LOGHOLDER_H_

#include <LogMessage.h>
#include <LogCore.h>
#include <memory>
#include <Tags.h>

#define LOG_DEBUG(args)																				\
{																									\
	std::shared_ptr<LogMessage> logMessage(new LogMessage({{"time", currentTime()}, {"severity", "Debug"}}));	\
	logMessage->stream() << args;\
	LogCore::instance().log(logMessage); \
}

#define LOG_INFO(args)																				\
{																									\
	std::shared_ptr<LogMessage> logMessage(new LogMessage({{"time", currentTime()}, {"severity", "Info"}}));	\
	logMessage->stream() << args;\
	LogCore::instance().log(logMessage); \
}

#endif /* LOGHOLDER_H_ */