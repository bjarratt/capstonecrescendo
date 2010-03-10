//
//  ReferenceType.m
//  ecologylabXML
//
//  Created by ecologylab on 1/20/10.file://localhost/output/ecologylab/tutorials/polymorphic/rogue/game2d/entity/Entity.h
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "ReferenceType.h"
#import "FieldDescriptor.h"

@implementation ReferenceType


#pragma mark ReferenceType - Default constructor cannot be called.
- (id) init {
	[self doesNotRecognizeSelector: _cmd];
	[self release];
	return nil;
}

#pragma mark ReferenceType - construction for the abstract class

- (id) initWithString: (NSString *) value {
	if ( (self = [super init]) ) {
		[self setInstance: value];
		[self setDefaultValue];
	}
	return self;
}

- (void) setField: (id) object fieldName: (const char *) fn {
	object_setInstanceVariable(object, fn, m_value);
}

- (void) setField: (id) object fieldName: (NSString *) fn value: (id) value {
	m_value = value;
	object_setInstanceVariable(object, [fn cStringUsingEncoding: NSASCIIStringEncoding], m_value);
}

- (void) appendValue: (NSMutableString *) buffy fieldDescriptor: (FieldDescriptor *) fd context: (id) context {
	id instance = [context valueForKey:[fd getFieldName]];
	[self appendValue: buffy context: instance];
}

- (void) appendValue: (NSMutableString *) buffy context: (id) instance {
	[buffy appendFormat:[instance description]];
}

- (BOOL) isDefaultValue: (FieldDescriptor *) fieldDescriptor context: (ElementState *) elementState {
	id instance = [elementState valueForKey:[fieldDescriptor getFieldName]];
	return instance == nil;
}

- (id) getValueFromString: (NSString *) value {
	[self setInstance: value];
	return m_value;
}

- (id) getInstance {
	return m_value;
}

#pragma mark ReferenceType - Abstract Functions (Inherting class must overload these functions)

- (void) setDefaultValue {
	[self doesNotRecognizeSelector: _cmd];
}

- (void) setInstance: (NSString *) value {
	[self doesNotRecognizeSelector: _cmd];
}

@end