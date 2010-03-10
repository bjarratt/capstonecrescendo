//
//  DoubleType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "DoubleType.h"

@implementation DoubleType

+ (id) doubleTypeWithString : (NSString *) value {
	return [[[DoubleType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = @"0";
}

- (void) setInstance: (NSString *) value {
	double *dValue = malloc( sizeof(double) );
	*dValue = [value floatValue];
	m_value = (id)dValue;
}

@end