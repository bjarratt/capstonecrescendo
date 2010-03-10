//
//  TypeType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/10/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "ReferenceTypeType.h"


@implementation ReferenceTypeType

+ (id) referenceTypeTypeWithString : (NSString *) value {
	return [[[ReferenceTypeType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = nil;
}

- (void) setInstance: (NSString *) value {
	m_value = [XMLTools typeWithString: value];
}

@end