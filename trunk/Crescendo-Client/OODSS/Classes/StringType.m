//
//  StringType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "StringType.h"


@implementation StringType

+ (id) stringTypeWithString : (NSString *) value {
	return [[[StringType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = nil;
}

- (void) setInstance: (NSString *) value {
	m_value = value;
}

@end