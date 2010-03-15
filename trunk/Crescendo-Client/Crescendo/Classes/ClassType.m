//
//  ClassType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "ClassType.h"


@implementation ClassType

+ (id) classTypeWithString : (NSString *) value {
	return [[[ClassType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = nil;
}

- (void) setInstance: (NSString *) value {
	Class *cPtr = malloc( sizeof(Class) );
	*cPtr = [XMLTools getClass: value];
	m_value = (id)cPtr;
}

@end