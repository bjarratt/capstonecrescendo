//
//  IntType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "IntType.h"

@implementation IntType

+ (id) intTypeWithString : (NSString *) value {
	return [[[IntType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = @"0";
}

- (void) setInstance: (NSString *) value {
	int *iValue = malloc( sizeof(int) );
	*iValue = [value intValue];
	m_value = (id)iValue;
}

@end