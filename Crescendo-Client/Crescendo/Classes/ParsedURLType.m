//
//  ParsedURLType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "ParsedURLType.h"
#import "FieldDescriptor.h"

@implementation ParsedURLType

+ (id) parsedURLTypeWithString : (NSString *) value {
	return [[[ParsedURLType alloc] initWithString: value] autorelease];
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = nil;
}

- (void) setInstance: (NSString *) value {
	m_value = [NSURL URLWithString: value];
}

@end