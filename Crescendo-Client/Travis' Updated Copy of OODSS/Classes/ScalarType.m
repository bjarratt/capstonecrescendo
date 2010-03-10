//
//  AbstractScalarType.m
//  ecologylabXML
//
//  Created by ecologylab on 1/20/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "ScalarType.h"
#import "FieldDescriptor.h"

@implementation ScalarType

#pragma mark AbstractScalarType - Default constructor cannot be called.

- (id) init {
	[self doesNotRecognizeSelector: _cmd];
	[self release];
	return nil;
}

#pragma mark AbstractScalarType - construction for the abstract class
- (id) initWithString: (NSString *) value {
	if ( (self = [super init]) ) {
		[self setInstance: value];
		[self setDefaultValue];
	}
	return self;
}

- (void) setField: (id) object fieldName: (const char *) fn {
	objc_msgSend(object, sel_getUid([XMLTools getSetterFunction: fn]), m_value);
}

- (void) setField: (id) object fieldName: (NSString *) fn value: (id) value {
	[self setInstance:[value description]];	
	objc_msgSend(object, sel_getUid([XMLTools getSetterFunction:[fn cStringUsingEncoding: NSASCIIStringEncoding]]), m_value);
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
	return instance == nil ||[[instance description] isEqualToString: DEFAULT_VALUE_STRING];
}

- (id) getValueFromString: (NSString *) value {
	[self setInstance: value];
	return m_value;
}

- (id) getInstance {
	return m_value;
}

#pragma mark AbstractScalarType - Abstract Functions (Inherting class must overload these functions)

- (void) setDefaultValue {
	[self doesNotRecognizeSelector: _cmd];
}

- (void) setInstance: (NSString *) value {
	[self doesNotRecognizeSelector: _cmd];
}

@end