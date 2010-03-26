//
//  AbstractScalarType.h
//  ecologylabXML
//
//  Created by ecologylab on 1/20/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import	<objc/message.h>
#import <Foundation/Foundation.h>

#import "Type.h"
#import "XMLTools.h"

@class FieldDescriptor;

@interface ScalarType : NSObject <Type> {
	id m_value;
	NSString *DEFAULT_VALUE_STRING;
}

- (id) initWithString: (NSString *) value;
- (id) getValueFromString: (NSString *) value;
- (void) setField: (id) object fieldName: (const char *) fn;
- (void) appendValue: (NSMutableString *) buffy fieldDescriptor: (FieldDescriptor *) fd context: (id) context;
- (id) getInstance;
- (void) setInstance: (NSString *) value;
- (void) setDefaultValue;

@end