//
//  FieldType.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "FieldType.h"


@implementation FieldType

+ (id) fieldTypeWithStringAndClass : (NSString *) value containerClass : (Class *) c {
	return [[[FieldType alloc] initWithStringAndClass: value containerClass: c] autorelease];
}

- (id) initWithStringAndClass: (NSString *) value containerClass: (Class *) c {
	if ( (self = [super initWithString: value]) ) {
		[self setInstance: value containerClass: c];
		[self setDefaultValue];
	}
	return self;
}

- (Ivar *) getValueFromString: (NSString *) value containerClass: (Class *) c {
	Ivar *iVar = malloc( sizeof(Ivar) );
	*iVar = class_getInstanceVariable(*c, [value cStringUsingEncoding: NSASCIIStringEncoding]);
	return iVar;
}

- (void) setDefaultValue {
	DEFAULT_VALUE_STRING = nil;
}

- (void) setInstance: (NSString *) value {
}

- (void) setInstance: (NSString *) value containerClass: (Class *) c {
	Ivar *iVar = malloc( sizeof(Ivar) );
	*iVar = class_getInstanceVariable(*c, [value cStringUsingEncoding: NSASCIIStringEncoding]);
	m_value = (id)iVar;
}

@end