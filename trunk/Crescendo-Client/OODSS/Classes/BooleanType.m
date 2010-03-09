//
//  BooleanType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "BooleanType.h"

@implementation BooleanType

+ (id) booleanTypeWithString : (NSString *) value {
	return [[[BooleanType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = @"false";
}

- (void) setInstance: (NSString *) value {
	BOOL *bValue = malloc( sizeof(BOOL) );
	*bValue = [value boolValue];
	m_value = (id)bValue;
}

@end