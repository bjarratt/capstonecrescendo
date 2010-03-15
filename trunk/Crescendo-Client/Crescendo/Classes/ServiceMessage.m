//
//  ServiceMessage.m
//  ecologylabXML
//
//  Created by William Hamilton on 1/22/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "ServiceMessage.h"


@implementation ServiceMessage

@synthesize timeStamp;
@synthesize uid;

- (void) setTimeStampWithReference: (long *) p_timeStamp{
	timeStamp = *p_timeStamp;
}

- (void) setUidWithReference: (long *) p_uid{
	uid = *p_uid;
}

- (id) initTestObject {
	if((self = [super init])){
		uid = 111;
		timeStamp = 4234234;
	}
	return self;
}

@end
