//
//  FloatType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "FloatType.h"

@implementation FloatType

+ (id) floatTypeWithString : (NSString *) value {
	return [[[FloatType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = @"0";
}

- (void) setInstance: (NSString *) value {
	float *fValue = malloc( sizeof(float) );
	*fValue = [value floatValue];
	m_value = (id)fValue;
}

@end