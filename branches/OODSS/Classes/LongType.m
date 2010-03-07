//
//  LongType.m
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "LongType.h"


@implementation LongType

+ (id) longTypeWithString : (NSString *) value {
	return [[[LongType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = @"0";
}

- (void) setInstance: (NSString *) value {
	long *fValue = malloc( sizeof(long) );
	*fValue = [value doubleValue];
	m_value = (id)fValue;
}

@end
